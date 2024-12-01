/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.core.base.http.response.ResponseStatusEnum;
import org.funcode.portal.server.common.core.config.ApplicationConfig;
import org.funcode.portal.server.common.core.constant.RedisKeyConstant;
import org.funcode.portal.server.common.core.constant.SecurityConstant;
import org.funcode.portal.server.common.core.security.service.IJwtService;
import org.funcode.portal.server.common.domain.security.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements IJwtService {

    private final UserDetailsServiceImpl userDetailsService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ApplicationConfig applicationConfig;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    @Transactional
    public void filterVerifyAccessToken(@NonNull String accessToken,
                                        @NonNull HttpServletRequest request,
                                        @NonNull HttpServletResponse response) throws IOException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
            try {
                String username = this.extractUserName(accessToken);
                if (StringUtils.isNotEmpty(username)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService
                        .loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                // 验证 token 是否有效
                if (this.isTokenValid(accessToken, userDetails)) {
                    // 保存登录状态到当前上下文
                    SecurityContextHolder.setContext(context);
                    response.setHeader(SecurityConstant.TOKEN_HEADER_KEY, accessToken);
                }
            }
        } catch (ExpiredJwtException e) {
            String username = e.getClaims().getSubject();
            // refresh-token的过期时间
            var refreshTokenExpired = applicationConfig.getSecurity().token().refreshExpiration();
            String refreshToken = redisTemplate.opsForValue().getAndExpire(RedisKeyConstant.TOKEN_KEY + username + ":" + accessToken, refreshTokenExpired, TimeUnit.MINUTES);
            if (StringUtils.isBlank(refreshToken)) {
                // Redis中不存在说明过期，需要重新登录
                SecurityContextHolder.clearContext();
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().write(mapper.writeValueAsString(ResponseResult.fail("请重新登录", ResponseStatusEnum.HTTP_STATUS_401)));
            } else if (Objects.equals(accessToken, refreshToken)) {
                // 相等情况重新签发token
                User userDetails = (User) userDetailsService
                        .loadUserByUsername(username);
                String newAccessToken = this.generateToken(userDetails);
                Cookie cookie = new Cookie(SecurityConstant.TOKEN_COOKIE_KEY, newAccessToken);
                cookie.setHttpOnly(true);
                response.addHeader(SecurityConstant.TOKEN_HEADER_KEY, newAccessToken);
                response.addCookie(cookie);
                redisTemplate.opsForValue().set(RedisKeyConstant.TOKEN_KEY + username + ":" + newAccessToken, newAccessToken, refreshTokenExpired, TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(RedisKeyConstant.TOKEN_TRANSITION_KEY + username + ":" + accessToken, newAccessToken, 2, TimeUnit.MINUTES);
                // 保存登录状态到当前上下文
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            } else {
                // refresh-token 不一致时，说明 token 已经被刷新了，当前为并发场景
                String refreshTransitionToken = redisTemplate.opsForValue().get(RedisKeyConstant.TOKEN_TRANSITION_KEY + username + ":" + accessToken);
                // 如果在过渡时间内，允许通过认证
                if (StringUtils.isNotBlank(refreshTransitionToken)) {
                    User userDetails = (User) userDetailsService
                            .loadUserByUsername(username);
                    Cookie cookie = new Cookie(SecurityConstant.TOKEN_COOKIE_KEY, refreshTransitionToken);
                    cookie.setHttpOnly(true);
                    response.addHeader(SecurityConstant.TOKEN_HEADER_KEY, refreshTransitionToken);
                    response.addCookie(cookie);
                    // 保存登录状态到当前上下文
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }
            }
        }
    }

    @Override
    public void successLoginHandler(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull Authentication authentication) throws IOException {
        // 获取返回的用户
        User currentUser = (User) authentication.getPrincipal();
        String accessToken = this.generateToken(currentUser);
        response.addHeader(SecurityConstant.TOKEN_HEADER_KEY, accessToken);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Cookie cookie = new Cookie(SecurityConstant.TOKEN_COOKIE_KEY, accessToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.setStatus(HttpStatus.OK.value());
        // 保存至Redis缓存起来
        redisTemplate.opsForValue().set(
                RedisKeyConstant.TOKEN_KEY + currentUser.getUsername() + ":" + accessToken,
                accessToken,
                applicationConfig.getSecurity().token().refreshExpiration(),
                TimeUnit.MINUTES);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(ResponseResult.success(currentUser)));
    }

    /**
     * 提取claims
     *
     * @param token           token
     * @param claimsResolvers claims解析器
     * @param <T>             类型
     * @return claims
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * 生成 access-token
     *
     * @param extraClaims 额外claims
     * @param userDetails 用户信息
     * @return access-token
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * applicationConfig.getSecurity().token().expiration()))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 提取过期时间
     *
     * @param token token
     * @return 过期时间
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 提取所有claims
     *
     * @param token token
     * @return claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 获取签名密钥
     *
     * @return 签名密钥
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(applicationConfig.getSecurity().token().signingKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

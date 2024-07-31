/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.config.ApplicationConfig;
import org.funcode.portal.server.common.core.constant.RedisKeyConstant;
import org.funcode.portal.server.common.core.constant.SecurityConstant;
import org.funcode.portal.server.common.core.security.domain.dto.User;
import org.funcode.portal.server.common.core.security.service.IJwtService;
import org.funcode.portal.server.common.core.security.service.impl.UserDetailsServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final IJwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ApplicationConfig applicationConfig;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String accessToken = request.getHeader(SecurityConstant.TOKEN_HEADER_KEY);
        final String username;
        // 没 token 场景为 登录 等，直接放行，因为后边有其他的过滤器
        if (StringUtils.isBlank(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        username = jwtService.extractUserName(accessToken);
        if (StringUtils.isNotEmpty(username)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            User userDetails = (User) userDetailsService
                    .loadUserByUsername(username);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
            // 验证 token 是否有效
            if (jwtService.isTokenValid(accessToken, userDetails)) {
                // 保存登录状态到当前上下文
                SecurityContextHolder.setContext(context);
            }
            if (jwtService.isTokenExpired(accessToken)) {
                // 已经过期进行是否可以续期判断
                // refresh-token的过期时间
                var refreshTokenExpired = applicationConfig.getSecurity().token().refreshExpiration();
                String refreshToken = redisTemplate.opsForValue().getAndExpire(RedisKeyConstant.TOKEN_KEY + username, refreshTokenExpired, TimeUnit.MINUTES);
                if (StringUtils.isBlank(refreshToken)) {
                    // Redis中不存在说明过期，需要重新登录
                    SecurityContextHolder.clearContext();
                    response.sendRedirect(StringUtils.isBlank(applicationConfig.getSecurity().loginPage()) ? "/login" : applicationConfig.getSecurity().loginPage());
                    return;
                } else if (Objects.equals(accessToken, refreshToken)) {
                    // 相等情况重新签发token
                    String newAccessToken = jwtService.generateToken(userDetails);
                    Cookie cookie = new Cookie(SecurityConstant.TOKEN_COOKIE_KEY, newAccessToken);
                    cookie.setHttpOnly(true);
                    response.addHeader(SecurityConstant.TOKEN_HEADER_KEY, newAccessToken);
                    response.addCookie(cookie);
                    redisTemplate.opsForValue().setIfAbsent(RedisKeyConstant.TOKEN_KEY + username, newAccessToken, refreshTokenExpired, TimeUnit.MINUTES);
                    redisTemplate.opsForValue().setIfAbsent(RedisKeyConstant.TOKEN_TRANSITION_KEY + username, accessToken, 2, TimeUnit.MINUTES);
                    // 保存登录状态到当前上下文
                    SecurityContextHolder.setContext(context);
                } else {
                    // refresh-token 不一致时，说明 token 已经被刷新了，当前为并发场景
                    String refreshTransitionToken = redisTemplate.opsForValue().get(RedisKeyConstant.TOKEN_TRANSITION_KEY + username);
                    // 如果在过渡时间内，允许通过认证
                    if (StringUtils.isNotBlank(refreshTransitionToken) && Objects.equals(accessToken, refreshTransitionToken)) {
                        // 保存登录状态到当前上下文
                        SecurityContextHolder.setContext(context);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}


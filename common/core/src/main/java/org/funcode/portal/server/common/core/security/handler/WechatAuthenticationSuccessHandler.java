/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.core.config.ApplicationConfig;
import org.funcode.portal.server.common.core.constant.RedisKeyConstant;
import org.funcode.portal.server.common.core.constant.SecurityConstant;
import org.funcode.portal.server.common.core.security.service.IJwtService;
import org.funcode.portal.server.common.domain.security.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WechatAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final IJwtService jwtService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ApplicationConfig applicationConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 获取返回的微信openid
        User currentUser = (User) authentication.getPrincipal();
        var accessToken = jwtService.generateToken(currentUser);
        response.addHeader(SecurityConstant.TOKEN_HEADER_KEY, accessToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
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
        response.getWriter().write(mapper.writeValueAsString(ResponseResult.success()));
    }
}

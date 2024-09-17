/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IJwtService {
    /**
     * 解析 token 获取 username
     *
     * @param token access-token
     * @return username
     */
    String extractUserName(String token);

    /**
     * 生成 access-token
     *
     * @param userDetails 用户信息
     * @return access-token
     */
    String generateToken(UserDetails userDetails);

    /**
     * access-token 是否合法
     *
     * @param token       access-token
     * @param userDetails 用户信息
     * @return 是否合法
     */
    boolean isTokenValid(String token, UserDetails userDetails);

    /**
     * access-token 是否过期
     *
     * @param token access-token
     * @return 是否过期
     */
    boolean isTokenExpired(String token);

    /**
     * 验证 access-token 并刷新
     *
     * @param token    access-token
     * @param request  请求体
     * @param response 响应体
     * @throws IOException 异常
     */
    void filterVerifyAccessToken(@NonNull String token, @NonNull HttpServletRequest request,
                                 @NonNull HttpServletResponse response) throws IOException;

    /**
     * 登录成功后拦截token
     *
     * @param request  请求体
     * @param response 响应体
     */
    void successLoginHandler(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Authentication authentication) throws IOException;

}

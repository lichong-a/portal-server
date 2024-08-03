/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.constant.SecurityConstant;
import org.funcode.portal.server.common.core.security.service.IJwtService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final IJwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String accessToken = request.getHeader(SecurityConstant.TOKEN_HEADER_KEY);
        // 没 token 场景为 登录 等，直接放行，因为后边有其他的过滤器
        if (StringUtils.isBlank(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtService.filterVerifyAccessToken(accessToken, request, response);
        filterChain.doFilter(request, response);
    }
}


/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.funcode.portal.server.common.core.security.domain.WechatAuthenticationToken;
import org.funcode.portal.server.common.core.security.handler.WechatAuthenticationFailureHandler;
import org.funcode.portal.server.common.core.security.handler.WechatAuthenticationSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.ObjectUtils;

/**
 * 微信登录认证
 *
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public class WechatAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * 微信登录路径
     */
    public static final String WECHAT_LOGIN_PATH = "/api/v1/auth/wechat/login";
    /**
     * 允许的请求方法
     */
    public static final String METHOD = "POST";
    /**
     * 参数名称
     */
    public static final String PARAM_KEY = "code";
    /**
     * 路径匹配
     */
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(WECHAT_LOGIN_PATH, METHOD);


    public WechatAuthenticationFilter(AuthenticationManager authenticationManager,
                                      WechatAuthenticationSuccessHandler wechatAuthenticationSuccessHandler,
                                      WechatAuthenticationFailureHandler wechatAuthenticationFailureHandler) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        setAuthenticationFailureHandler(wechatAuthenticationFailureHandler);
        setAuthenticationSuccessHandler(wechatAuthenticationSuccessHandler);
    }

    /**
     * 从请求中取出code参数，向微信服务器发送请求，如果成功获取到openID，则创建一个未认证的 WechatAuthenticationToken ， 并将其提交给 AuthenticationManager
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!METHOD.equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        final String code = request.getParameter(PARAM_KEY);
        if (ObjectUtils.isEmpty(code)) {
            throw new AuthenticationServiceException("code 不允许为空");
        }
        // 创建一个未认证的token，放入code
        final WechatAuthenticationToken token = WechatAuthenticationToken.unauthenticated(code);
        // 设置details
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
        // 将token提交给 AuthenticationManager
        return this.getAuthenticationManager().authenticate(token);
    }
}
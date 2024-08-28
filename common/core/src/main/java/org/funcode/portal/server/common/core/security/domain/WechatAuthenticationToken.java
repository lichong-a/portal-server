/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.domain;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 微信登录认证Token
 *
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Getter
public class WechatAuthenticationToken extends AbstractAuthenticationToken {
    private final String openId;
    private final Object principal;

    /**
     * 未认证的Token
     *
     * @param openId openId
     */
    public WechatAuthenticationToken(String openId) {
        super(null);
        this.openId = openId;
        this.principal = openId;
        setAuthenticated(false);
    }

    /**
     * 已认证的token
     *
     * @param openId      openId
     * @param authorities 权限
     */
    public WechatAuthenticationToken(String openId, Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.openId = openId;
        this.principal = principal;
        setAuthenticated(true);
    }

    /**
     * 未认证的Token
     *
     * @param openId openId
     */
    public static WechatAuthenticationToken unauthenticated(String openId) {
        return new WechatAuthenticationToken(openId);
    }

    /**
     * 已认证的token
     *
     * @param openId      openId
     * @param authorities 权限
     */
    public static WechatAuthenticationToken authenticated(String openId, Object principal, Collection<? extends GrantedAuthority> authorities) {
        return new WechatAuthenticationToken(openId, principal, authorities);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}

/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {

    private final Security security;

    /**
     * @param adminUsername    管理员用户名
     * @param adminPassword    管理员密码
     * @param token            token相关配置
     * @param logoutSuccessUrl 注销成功跳转地址
     * @param loginPage        登录页地址，默认：/login
     */
    public record Security(String adminUsername,
                           String adminPassword,
                           Token token,
                           String logoutSuccessUrl,
                           String loginPage) {
        /**
         * @param signingKey 签名密钥
         * @param expiration access-token过期时间，单位：分钟
         * @param refreshExpiration refresh-token的过期时间，单位：分钟
         */
        public record Token(String signingKey,
                            Long expiration,
                            Long refreshExpiration) {
        }
    }

}

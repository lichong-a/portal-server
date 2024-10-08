/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {

    @NestedConfigurationProperty
    private final Security security;

    public static final String WECHAT_LOGIN_URL_TEMPLATE = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}";

    /**
     * @param adminUsername              管理员用户名
     * @param adminPassword              管理员密码
     * @param token                      token相关配置
     * @param weChat                     微信小程序
     * @param logoutSuccessUrl           注销成功跳转地址
     * @param loginPage                  登录页地址，默认：/login
     * @param corsAllowedHeaders         允许跨域的请求头
     * @param corsAllowedOriginPatterns  允许跨域的域名Pattern
     * @param corsAllowedMethods         允许跨域的请求方法
     * @param corsExposeHeaders          暴露给前端的请求头
     * @param corsAllowCredentials       是否允许跨域携带cookie
     */
    public record Security(String adminUsername,
                           String adminPassword,
                           @NestedConfigurationProperty Token token,
                           @NestedConfigurationProperty WeChat weChat,
                           String logoutSuccessUrl,
                           String loginPage,
                           List<String> corsAllowedHeaders,
                           List<String> corsAllowedOriginPatterns,
                           List<String> corsAllowedMethods,
                           List<String> corsExposeHeaders,
                           boolean corsAllowCredentials) {
        /**
         * @param signingKey        签名密钥
         * @param expiration        access-token过期时间，单位：分钟
         * @param refreshExpiration refresh-token的过期时间，单位：分钟
         */
        public record Token(String signingKey,
                            Long expiration,
                            Long refreshExpiration) {
        }

        /**
         * @param appId     小程序ID
         * @param appSecret 小程序密钥
         */
        public record WeChat(String appId,
                             String appSecret) {
        }
    }

}

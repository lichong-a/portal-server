package org.funcode.portal.server.common.core.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application")
@AllArgsConstructor
public class ApplicationConfig {

    private final Security security;

    /**
     * @param adminUsername 管理员用户名
     * @param adminPassword 管理员密码
     * @param token         token相关配置
     */
    public record Security(String adminUsername,
                           String adminPassword,
                           Token token) {
        /**
         * @param signingKey 签名密钥
         * @param expiration token过期时间，单位：分钟
         */
        public record Token(String signingKey,
                            Long expiration) {
        }
    }

}

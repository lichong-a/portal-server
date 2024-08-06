/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@ConfigurationProperties(prefix = "application.cos")
@Configuration
public class CosConfig {

    /**
     * 腾讯云控制台项目配置secretId
     */
    private String secretId;

    /**
     * 腾讯云控制台项目配置secretKey
     */
    private String secretKey;
    /**
     * 存储桶地域
     */
    private String region;
    /**
     * 存储桶名称
     */
    private String bucketName = "ielts-online-1312690188";
    /**
     * 业务项目名称
     */
    private String projectName = "ielts-online";
    /**
     * 公共目录
     */
    private String common = "common";
    /**
     * 图片大小
     */
    private String imageSize = "2";
    /**
     * CDN加速域名
     */
    private String prefixDomain = "";
    /**
     * 过期时间
     */
    private Long expiration = 60L;
}

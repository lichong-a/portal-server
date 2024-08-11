/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new JacksonConfig.String2LocalDateConverter());
        registry.addConverter(new JacksonConfig.String2LocalDateTimeConverter());
        registry.addConverter(new JacksonConfig.String2LocalTimeConverter());
        registry.addConverter(new JacksonConfig.String2DateConverter());
    }

}

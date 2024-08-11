package org.funcode.portal.server.common.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

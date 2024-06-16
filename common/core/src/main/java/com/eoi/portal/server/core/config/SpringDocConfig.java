package com.eoi.common.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(name = "门户_token", type = SecuritySchemeType.HTTP, paramName = "Authorization", scheme = "Bearer", in = SecuritySchemeIn.HEADER)
@SecurityScheme(name = "产线UA_token", type = SecuritySchemeType.APIKEY, paramName = "UA", in = SecuritySchemeIn.COOKIE)
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("门户")
                        .description("门户API文档")
                        .version("dev")
                        .license(new License()
                                .name("")
                                .url(""))
                        .contact(new Contact()
                                .name("LiChong")
                                .url("https://lichong.work")
                                .email("chong.li@eoitek.com")))
                .security(List.of(new SecurityRequirement().addList("门户_token", "产线UA_token")));
    }
}

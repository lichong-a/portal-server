package org.funcode.portal.server.common.core.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.funcode.portal.server.common.core.constant.SecurityConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(name = "token", type = SecuritySchemeType.APIKEY, paramName = SecurityConstant.TOKEN_HEADER_KEY, in = SecuritySchemeIn.HEADER)
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("接口文档")
                        .description("API Documentation")
                        .version("dev")
                        .license(new License()
                                .name("")
                                .url(""))
                        .contact(new Contact()
                                .name("LiChong")
                                .url("https://lichong.work")
                                .email("mail@lichong.host")))
                .security(List.of(new SecurityRequirement().addList("token")));
    }
}

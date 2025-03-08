package com.rudy.file.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SWAGGER_URL = "/v2/api-docs";
    private static final String SWAGGER_TITLE = "File Server API";
    private static final String SWAGGER_VERSION = "1.0";
    private static final String SWAGGER_DESCRIPTION = "File Server API";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(getDescription())
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // 모든 API에 적용
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

    private static Info getDescription() {
        return new Info()
                .title(SWAGGER_TITLE)
                .version(SWAGGER_VERSION)
                .description(SWAGGER_DESCRIPTION);
    }
}

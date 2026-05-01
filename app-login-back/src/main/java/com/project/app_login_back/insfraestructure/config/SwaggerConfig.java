package com.project.app_login_back.insfraestructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_AUTH = "BearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Plataforma de Información")
                        .version("1.0")
                        .description("Documentación de los endpoints del monolito"))
                .components(new Components()
                        .addSecuritySchemes(BEARER_AUTH, new SecurityScheme()
                                .name(BEARER_AUTH)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("1-Autenticacion")
                .pathsToMatch(
                        "/api/v1/login/token",
                        "/api/v1/user/all",
                        "/api/v1/user/in",
                        "/api/v1/user/updaterol/**"
                )
                // NO agregamos seguridad aquí, por lo tanto aparecerán sin candado
                .build();
    }

    @Bean
    public GroupedOpenApi infoApi() {
        return GroupedOpenApi.builder()
                .group("2-Informacion-Plataforma")
                .pathsToMatch(
                        "/api/v1/login/token", // Revisa si este debe estar aquí o solo en public
                        "/api/v1/login/in"
                )
                // AGREGAMOS la exigencia del token solo para este grupo
                .addOpenApiCustomizer(openApi -> openApi.addSecurityItem(
                        new SecurityRequirement().addList(BEARER_AUTH)))
                .build();
    }
}



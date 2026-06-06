package com.project.app_login_back.insfraestructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Locale;

@Configuration
@Slf4j
public class SwaggerConfig {

    private static final String BEARER_AUTH = "BearerAuth";
    private final MessageSource messageSource;

    public SwaggerConfig(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        // --- BLOQUE DE DIAGNÓSTICO TEMPORAL ---
        try {
            String pruebaEs = messageSource.getMessage("swagger.operation.loginToken.summary", null, "ARCHIVO_NO_ENCONTRADO_ES", Locale.forLanguageTag("es"));
            String pruebaEn = messageSource.getMessage("swagger.operation.loginToken.summary", null, "ARCHIVO_NO_ENCONTRADO_EN", Locale.ENGLISH);
            log.info("==============================================");
            log.info("TEST TRADUCCIÓN ES: " + pruebaEs);
            log.info("TEST TRADUCCIÓN EN: " + pruebaEn);
            log.info("==============================================");
        } catch (Exception e) {
            log.error("ERROR CRÍTICO LEYENDO PROPERTIES: " + e.getMessage());
        }


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
                .addOpenApiCustomizer(openApi -> {
                    Locale locale = LocaleContextHolder.getLocale();

                    // 1. Traducimos el tag del grupo
                    String loginDesc = messageSource.getMessage("swagger.tag.login.description", null, "Login operations", locale);
                    openApi.tags(List.of(new Tag().name("login").description(loginDesc)));

                    // 2. LLAMADO CRUCIAL: Forzamos la traducción de los endpoints en este grupo
                    traducirOperacionesDelGrupo(openApi, locale);
                })
                .build();
    }

    @Bean
    public GroupedOpenApi infoApi() {
        return GroupedOpenApi.builder()
                .group("2-Informacion-Plataforma")
                .pathsToMatch(
                        "/api/v1/login/token",
                        "/api/v1/login/in",
                        "/api/v1/signup/in"
                )
                .addOpenApiCustomizer(openApi -> {
                    // Mantenemos tu seguridad original
                    openApi.addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH));

                    Locale locale = LocaleContextHolder.getLocale();

                    // 1. Traducimos el tag del grupo
                    String loginDesc = messageSource.getMessage("swagger.tag.login.description", null, "Login operations", locale);
                    openApi.tags(List.of(new Tag().name("login").description(loginDesc)));

                    // 2. LLAMADO CRUCIAL: Forzamos la traducción de los endpoints en este grupo
                    traducirOperacionesDelGrupo(openApi, locale);
                })
                .build();
    }

    // Convertido a método privado utilitario para que actúe DENTRO del ciclo de vida del grupo
    private void traducirOperacionesDelGrupo(OpenAPI openApi, Locale locale) {
        if (openApi.getPaths() == null) return;

        openApi.getPaths().forEach((pathName, pathItem) -> builderPathlem(locale, pathItem));
    }

    private void builderPathlem(Locale locale, PathItem pathItem) {
        pathItem.readOperations().forEach(operation -> {
            String opId = operation.getOperationId();

            if (opId != null && !opId.isEmpty()) {
                String summaryKey = "swagger.operation." + opId + ".summary";
                String descKey = "swagger.operation." + opId + ".description";

                // Si no encuentra la llave en tu .properties, mantendrá el texto que pusiste en el Controller
                String translatedSummary = messageSource.getMessage(summaryKey, null, operation.getSummary(), locale);
                String translatedDesc = messageSource.getMessage(descKey, null, operation.getDescription(), locale);

                operation.setSummary(translatedSummary);
                operation.setDescription(translatedDesc);
            }
        });
    }
}

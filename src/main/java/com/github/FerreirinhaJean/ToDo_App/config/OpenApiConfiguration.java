package com.github.FerreirinhaJean.ToDo_App.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Todo App - API",
                version = "v1",
                contact = @Contact(
                        name = "FerreirinhaJean",
                        email = "ferreirinha.jean@example.com",
                        url = "todoapp.com"
                ),
                description = "This API provides endpoints to manage a Todo application. It allows users to create, retrieve, update, and delete tasks, as well as mark them as completed or pending. The API is designed to be simple, RESTful, and easy to integrate with frontend or mobile applications.\n" +
                        "\n" +
                        "It supports common Todo features such as task titles, descriptions, status tracking, and timestamps, enabling efficient task organization and productivity management.",
                summary = "Todo Application API"
        )
)
public class OpenApiConfiguration {

    @Value("${spring.application.base-url}")
    private String baseUrl;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("oauth2",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .flows(new OAuthFlows()
                                                .authorizationCode(new OAuthFlow()
                                                        .authorizationUrl(baseUrl + "/oauth2/authorize")
                                                        .tokenUrl(baseUrl + "/oauth2/token")
                                                )
                                        )
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("oauth2"));
    }
}

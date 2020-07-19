package com.ez2pay.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "EZ2PAY API",
                version = "2.0",
                description = "Documentation EZ2PAY API v2.0",
                contact = @Contact(email = "hiep.ho@wirecard.com", name = "Wirecard Vietnam", url = "https://www.ez2pay.com"),
                license = @License(name = "EZ2PAY License", url = "https://www.ez2pay.com"),
                termsOfService = "https://www.ez2pay.com"
        ),
        servers = @Server(url = "http://localhost:8080")
)
public class OpenAPIConfig {

    @Bean
    public GroupedOpenApi customerAPI() {
        return GroupedOpenApi.builder()
                .group("Customer")
                .pathsToMatch("/**/customer/**")
                .build();
    }

    @Bean
    public GroupedOpenApi inventoryAPI() {
        return GroupedOpenApi.builder()
                .group("Inventory")
                .pathsToMatch("/**/inventory/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderAPI() {
        return GroupedOpenApi.builder()
                .group("Order")
                .pathsToMatch("/**/order/**")
                .build();
    }

    @Bean
    public GroupedOpenApi v1API() {
        String[] paths = {"/v1/**"};
        String[] packagesToscan = {"com.ez2pay"};
        return GroupedOpenApi.builder()
                .addOpenApiCustomiser(new OpenApiCustomiser() {
                    @Override
                    public void customise(OpenAPI openApi) {
                        openApi.info(new io.swagger.v3.oas.models.info.Info()
                                .version("1.0")
                                .title("EZ2PAY API")
                                .description("Documentation EZ2PAY API v1.0")
                                .termsOfService("https://www.ez2pay.com")
                                .license(new io.swagger.v3.oas.models.info.License()
                                        .name("EZ2PAY License")
                                        .url("https://www.ez2pay.com")
                                )
                                .contact(new io.swagger.v3.oas.models.info.Contact()
                                        .name("Wirecard Vietnam")
                                        .email("hiep.ho@wirecard.com")
                                        .url("https://www.ez2pay.com")
                                )
                        );
                    }
                })
                .group("APIs (Version 1)")
                .pathsToMatch(paths)
                .packagesToScan(packagesToscan)
                .build();
    }

    @Bean
    public GroupedOpenApi v2API() {
        String[] paths = {"/v2/**"};
        String[] packagesToscan = {"com.ez2pay"};
        return GroupedOpenApi.builder()
                .group("APIs (Version 2)")
                .pathsToMatch(paths)
                .packagesToScan(packagesToscan)
                .build();
    }

}

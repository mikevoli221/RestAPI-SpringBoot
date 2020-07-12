package com.ez2pay.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.GroupedOpenApi;
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
    GroupedOpenApi personAPI() {
        return GroupedOpenApi.builder()
                .group("Person")
                .pathsToMatch("/**/person/**")
                .build();
    }
}

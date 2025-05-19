package com.pascal.ptm.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(new Info().title("PTM")
                        .description("Backend APIs PTM")
                        .version("v1.0.0")
                        .contact(new Contact().name("Pascal College").url("https://pascalcollge.edu.np/").email("info@pascalcollege.edu.np"))
                        .license(new License().name("License").url("/")))
                .externalDocs(new ExternalDocumentation().description("Expense Management App Documentation")
                        .url("http://localhost:8181/swagger-ui/index.html"));
    }

}

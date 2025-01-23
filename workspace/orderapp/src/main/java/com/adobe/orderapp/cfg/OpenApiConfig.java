package com.adobe.orderapp.cfg;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.hibernate.annotations.SecondaryRow;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Banuprakash",
                        email = "banu@lucidatechnologies.com",
                        url = "https://lucidatechnologies.com"
                ),
                description = "Documentation for Shopping Application",
                title = "OpenAPI Specification - Shopapp",
                version = "1.0.0",
                license = @License(name = "MIT license"),
                termsOfService = "Terms of Service..."
        ),
        servers = {
                @Server(
                        description = "Local Environment",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production Environment",
                        url = "https://luidatechnologies.com/"
                )
        }

)
@SecurityScheme(
    name = "BearerAuth",
        description = "JWT Token authorization",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}


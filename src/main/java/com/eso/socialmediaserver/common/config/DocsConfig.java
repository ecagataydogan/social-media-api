package com.eso.socialmediaserver.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class DocsConfig {

    @Bean
    public OpenAPI customizeOpenAPI() {
        String bearerAuth = "bearerAuth";

        OpenAPI openAPI = new OpenAPI();
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList(bearerAuth);
        openAPI.addSecurityItem(securityRequirement);

        Components components = new Components();

        SecurityScheme bearerSecurityScheme = new SecurityScheme();
        bearerSecurityScheme.setName(bearerAuth);
        bearerSecurityScheme.setType(SecurityScheme.Type.HTTP);
        bearerSecurityScheme.setDescription("Bearer auth for internal calls");
        bearerSecurityScheme.setScheme("bearer");
        bearerSecurityScheme.setBearerFormat("JWT");
        components.addSecuritySchemes(bearerAuth, bearerSecurityScheme);

        openAPI.setComponents(components);

        Info info  = new Info();
        info.setTitle("Social Media Server");
        info.setDescription("Social Media");
        info.setVersion("1");
        Contact contact = new Contact()
                .name("Eşref Çağatay Doğan")
                .email("ecagataydogan@gmail.com")
                .url("http://example.com");
        info.setContact(contact);
        openAPI.setInfo(info);
        openAPI.setSpecVersion(SpecVersion.V30);

        return openAPI;
    }
}
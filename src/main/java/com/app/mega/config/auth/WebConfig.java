package com.app.mega.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "https://admin.megamega-app.com",
                        "http://admin.megamega-app.com",
                        "https://user.megamega-app.com",
                        "http://user.megamega-app.com",
                        "http://localhost:3000",
                        "http://localhost:3001",
                        "http://localhost:3002"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH", "get","post","put","delete","patch")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
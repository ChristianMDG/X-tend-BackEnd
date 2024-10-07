package com.X_Tends.Teams.library_management;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://127.0.0.1:5501","http://127.0.0.1:5500","https://christianmdg.github.io","http://localhost:63342","https://christianmdg.github.io/X-Tends-Team/")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Assurez-vous que le chemin du répertoire correspond à l'endroit où vous stockez les images
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("C:\\Library backup\\library-management\\Gestion De Library X-Tends 1.0\\uploads");   // Remplacez par le chemin absolu
    }
}

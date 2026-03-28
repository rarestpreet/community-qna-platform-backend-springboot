package com.project.hearmeout_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration
                .setAllowedOrigins(List.of("http://localhost:5173", "https://hearmeout-frontend.onrender.com"));
        corsConfiguration
                .setAllowedMethods(List.of("PUT", "POST", "GET", "DELETE", "OPTIONS"));
        corsConfiguration
                .setAllowedHeaders(List.of("*"));
        corsConfiguration
                .setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        //cors config requires corsfilter and not CorsConfigSources
        return new CorsFilter(source);
    }
}

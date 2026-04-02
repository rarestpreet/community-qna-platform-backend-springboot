package com.project.hearmeout_backend.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cookie.creation")
@Data
public class CookieProperties {
    private String sameSite;
    private boolean secure;
}

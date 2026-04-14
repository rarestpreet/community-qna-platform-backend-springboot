package com.project.hearmeout_backend.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cookie.creation")
@Getter
@Setter
public class CookieProperties {
    private String sameSite;
    private boolean secure;
}

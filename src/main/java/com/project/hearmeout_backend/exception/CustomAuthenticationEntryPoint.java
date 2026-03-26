package com.project.hearmeout_backend.exception;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@NullMarked
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // improve filter chain exception handling
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        response.getWriter().write("""
        {
            "status": 401,
            "error": "Unauthorized",
            "message": "Invalid or missing token"
        }
        """);
    }
}

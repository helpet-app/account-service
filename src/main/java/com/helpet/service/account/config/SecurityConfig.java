package com.helpet.service.account.config;

import com.helpet.security.config.DefaultSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig extends DefaultSecurityConfig {
    private final String[] publicPaths = {
            "/auth/sign-up/**",
            "/auth/sign-in/**",
            "/auth/token/refresh/**",
            "/swagger-ui/**",
            "/api-docs/**"
    };

    @Override
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers(publicPaths)
                                                                                                     .permitAll());

        return super.securityFilterChain(http);
    }
}

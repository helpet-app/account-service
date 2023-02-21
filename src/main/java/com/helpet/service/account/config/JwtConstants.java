package com.helpet.service.account.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "auth.jwt")
@Configuration
public class JwtConstants {
    private String issuer;

    private Long accessExpiresIn;

    private Long refreshExpiresIn;
}
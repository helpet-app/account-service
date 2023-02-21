package com.helpet.service.account.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Getter
@Setter
@ConfigurationProperties(prefix = "auth.keys")
@Configuration
public class RsaKeys {
    private RSAPublicKey accessTokenPublicKey;

    private RSAPrivateKey accessTokenPrivateKey;

    private RSAPublicKey refreshTokenPublicKey;

    private RSAPrivateKey refreshTokenPrivateKey;
}

package com.helpet.service.account.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwkConfig {
    private final RsaKeys rsaKeys;

    @Autowired
    public JwkConfig(RsaKeys rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    @Qualifier("jwkAccessTokenSource")
    @Bean
    public JWKSource<SecurityContext> jwkAccessTokenSource() {
        JWK jwk = new RSAKey.Builder(rsaKeys.getAccessTokenPublicKey()).privateKey(rsaKeys.getAccessTokenPrivateKey()).build();
        return new ImmutableJWKSet<>(new JWKSet(jwk));
    }

    @Qualifier("jwkRefreshTokenSource")
    @Bean
    public JWKSource<SecurityContext> jwkRefreshTokenSource() {
        JWK jwk = new RSAKey.Builder(rsaKeys.getRefreshTokenPublicKey()).privateKey(rsaKeys.getRefreshTokenPrivateKey()).build();
        return new ImmutableJWKSet<>(new JWKSet(jwk));
    }
}

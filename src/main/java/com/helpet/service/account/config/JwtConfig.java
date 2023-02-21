package com.helpet.service.account.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class JwtConfig {
    @Primary
    @Bean
    public JwtEncoder jwtAccessTokenEncoder(@Qualifier("jwkAccessTokenSource") JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Primary
    @Bean
    public JwtDecoder jwtAccessTokenDecoder(@Autowired RsaKeys rsaKeys) {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.getAccessTokenPublicKey()).build();
    }

    @Qualifier("jwtRefreshTokenEncoder")
    @Bean
    public JwtEncoder jwtRefreshTokenEncoder(@Qualifier("jwkRefreshTokenSource") JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Qualifier("jwtRefreshTokenDecoder")
    @Bean
    public JwtDecoder jwtRefreshTokenDecoder(@Autowired RsaKeys rsaKeys) {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.getRefreshTokenPublicKey()).build();
    }
}

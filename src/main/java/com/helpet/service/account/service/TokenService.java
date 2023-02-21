package com.helpet.service.account.service;

import com.helpet.service.account.config.JwtConstants;
import com.helpet.service.account.store.model.Account;
import com.helpet.service.account.store.model.Session;
import com.helpet.service.account.web.dto.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class TokenService {
    private final JwtConstants jwtConstants;

    private final JwtEncoder jwtAccessTokenEncoder;

    private final JwtEncoder jwtRefreshTokenEncoder;

    @Autowired
    public TokenService(JwtConstants jwtConstants,
                        JwtEncoder jwtAccessTokenEncoder,
                        @Qualifier("jwtRefreshTokenEncoder") JwtEncoder jwtRefreshTokenEncoder) {
        this.jwtConstants = jwtConstants;
        this.jwtAccessTokenEncoder = jwtAccessTokenEncoder;
        this.jwtRefreshTokenEncoder = jwtRefreshTokenEncoder;
    }

    public JwtConstants getJwtConstants() {
        return jwtConstants;
    }

    public TokenResponse generateTokenResponse(Account account, Session session) {
        return TokenResponse.builder()
                            .accessToken(generateAccessToken(account, session).getTokenValue())
                            .accessExpiresIn(jwtConstants.getAccessExpiresIn())
                            .refreshToken(generateRefreshToken(account, session).getTokenValue())
                            .refreshExpiresIn(jwtConstants.getRefreshExpiresIn())
                            .build();
    }

    public Jwt generateAccessToken(Account account, Session session) {
        JwsHeader headers = defaultHeaders();

        Instant issuedAt = session.getIssuedAt();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .id(UUID.randomUUID().toString())
                                          .issuer(jwtConstants.getIssuer())
                                          .issuedAt(issuedAt)
                                          .expiresAt(issuedAt.plus(jwtConstants.getAccessExpiresIn(), ChronoUnit.SECONDS))
                                          .subject(account.getId().toString())
                                          .claim("typ", "Bearer")
                                          .claim("sid", session.getId())
                                          .claim("username", account.getUsername())
                                          .claim("fullName", account.getName())
                                          .claim("email", account.getEmail())
                                          .claim("emailVerified", account.getIsEmailVerified())
                                          .claim("roles", account.getRoles())
                                          .build();

        return jwtAccessTokenEncoder.encode(JwtEncoderParameters.from(headers, claims));
    }

    public Jwt generateRefreshToken(Account account, Session session) {
        JwsHeader headers = defaultHeaders();

        Instant issuedAt = session.getIssuedAt();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .id(UUID.randomUUID().toString())
                                          .issuer(jwtConstants.getIssuer())
                                          .issuedAt(issuedAt)
                                          .expiresAt(issuedAt.plus(jwtConstants.getRefreshExpiresIn(), ChronoUnit.SECONDS))
                                          .subject(account.getId().toString())
                                          .claim("typ", "Refresh")
                                          .claim("sid", session.getId())
                                          .build();

        return jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(headers, claims));
    }

    private JwsHeader defaultHeaders() {
        return JwsHeader.with(SignatureAlgorithm.RS256)
                        .type("JWT")
                        .build();
    }
}

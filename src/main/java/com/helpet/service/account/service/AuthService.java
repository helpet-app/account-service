package com.helpet.service.account.service;

import com.helpet.exception.*;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.account.dto.request.RefreshTokenRequest;
import com.helpet.service.account.dto.request.SignInRequest;
import com.helpet.service.account.dto.request.SignUpRequest;
import com.helpet.service.account.dto.response.TokenResponse;
import com.helpet.service.account.service.error.UnauthorizedLocalizedError;
import com.helpet.service.account.storage.model.Account;
import com.helpet.service.account.storage.model.Session;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {
    private final TokenService tokenService;

    private final AccountService accountService;

    private final SessionService sessionService;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final JwtAuthenticationProvider jwtRefreshTokenAuthenticationProvider;

    @Autowired
    public AuthService(TokenService tokenService,
                       AccountService accountService,
                       SessionService sessionService,
                       DaoAuthenticationProvider daoAuthenticationProvider,
                       @Qualifier("jwtRefreshTokenAuthenticationProvider") JwtAuthenticationProvider jwtRefreshTokenAuthenticationProvider) {
        this.tokenService = tokenService;
        this.accountService = accountService;
        this.sessionService = sessionService;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.jwtRefreshTokenAuthenticationProvider = jwtRefreshTokenAuthenticationProvider;
    }

    public TokenResponse signUp(HttpServletRequest httpServletRequest, SignUpRequest signUpInfo) throws ConflictLocalizedException {
        Account newAccount = accountService.createAccount(signUpInfo);

        Session newSession = sessionService.createSession(newAccount.getId(), httpServletRequest, tokenService.getJwtConstants().getRefreshExpiresIn());

        return tokenService.generateTokenResponse(newAccount, newSession);
    }

    public TokenResponse signIn(HttpServletRequest httpServletRequest, SignInRequest signInInfo) throws NotFoundLocalizedException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInInfo.getUsername(),
                                                                                                          signInInfo.getPassword());
        daoAuthenticationProvider.authenticate(authenticationToken);

        Account account = accountService.getAccountByUsername(signInInfo.getUsername());

        Session newSession = sessionService.createSession(account.getId(), httpServletRequest, tokenService.getJwtConstants().getRefreshExpiresIn());

        return tokenService.generateTokenResponse(account, newSession);
    }

    public TokenResponse refreshToken(HttpServletRequest httpServletRequest,
                                      RefreshTokenRequest refreshTokenInfo) throws UnauthorizedLocalizedException {
        BearerTokenAuthenticationToken authenticationToken = new BearerTokenAuthenticationToken(refreshTokenInfo.getRefreshToken());

        Authentication authentication = jwtRefreshTokenAuthenticationProvider.authenticate(authenticationToken);

        Jwt jwt = (Jwt) authentication.getCredentials();

        UUID accountId = JwtPayloadExtractor.extractSubject(jwt);
        UUID sessionId = JwtPayloadExtractor.extractSessionId(jwt);

        try {
            Session session = sessionService.extendSession(sessionId, httpServletRequest, tokenService.getJwtConstants().getRefreshExpiresIn());

            Account account = accountService.getAccount(accountId);

            return tokenService.generateTokenResponse(account, session);
        } catch (LocalizedException ex) {
            throw new UnauthorizedLocalizedException(UnauthorizedLocalizedError.TOKEN_IS_INVALID);
        }
    }

    public void signOut(UUID accountId, UUID sessionId) throws NotFoundLocalizedException {
        sessionService.leaveAccountSession(accountId, sessionId);
    }
}

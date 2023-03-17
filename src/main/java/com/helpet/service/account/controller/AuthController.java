package com.helpet.service.account.controller;

import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.account.dto.request.SignInRequest;
import com.helpet.service.account.service.AuthService;
import com.helpet.service.account.dto.request.RefreshTokenRequest;
import com.helpet.service.account.dto.request.SignUpRequest;
import com.helpet.service.account.dto.response.TokenResponse;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseBody> signUp(HttpServletRequest httpServletRequest, @RequestBody SignUpRequest signUpRequest) {
        TokenResponse tokenResponse = authService.signUp(httpServletRequest, signUpRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(tokenResponse);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseBody> signIn(HttpServletRequest httpServletRequest, @RequestBody SignInRequest signInRequest) {
        TokenResponse tokenResponse = authService.signIn(httpServletRequest, signInRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(tokenResponse);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<ResponseBody> refreshToken(HttpServletRequest httpServletRequest, @RequestBody RefreshTokenRequest refreshTokenRequest) {
        TokenResponse tokenResponse = authService.refreshToken(httpServletRequest, refreshTokenRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(tokenResponse);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<ResponseBody> signOut(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID accountId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        UUID sessionId = JwtPayloadExtractor.extractSessionId(jwtAuthenticationToken.getToken());
        authService.signOut(accountId, sessionId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}

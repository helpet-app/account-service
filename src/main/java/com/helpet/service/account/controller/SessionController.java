package com.helpet.service.account.controller;

import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.account.mapper.SessionMapper;
import com.helpet.service.account.service.SessionService;
import com.helpet.service.account.storage.model.Session;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/account/me/sessions")
@RestController
public class SessionController {
    private final SessionService sessionService;

    private final SessionMapper sessionMapper;

    public SessionController(SessionService sessionService, SessionMapper sessionMapper) {
        this.sessionService = sessionService;
        this.sessionMapper = sessionMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getSessions(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID accountId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        List<Session> sessions = sessionService.getAccountSessions(accountId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(sessionMapper.mapCollection(sessions));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{session-id}")
    public ResponseEntity<ResponseBody> getSession(@PathVariable("session-id") UUID sessionId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID accountId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Session session = sessionService.getAccountSession(accountId, sessionId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(sessionMapper.map(session));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{session-id}")
    public ResponseEntity<ResponseBody> leaveSession(@PathVariable("session-id") UUID sessionId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID accountId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        sessionService.leaveAccountSession(accountId, sessionId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseBody> leaveAllSessionsExceptCurrent(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID accountId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        UUID sessionId = JwtPayloadExtractor.extractSessionId(jwtAuthenticationToken.getToken());
        sessionService.leaveAllAccountSessionsExceptCurrent(accountId, sessionId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}

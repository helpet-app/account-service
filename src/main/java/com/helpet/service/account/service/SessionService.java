package com.helpet.service.account.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.account.service.error.NotFoundLocalizedError;
import com.helpet.service.account.storage.model.Session;
import com.helpet.service.account.storage.repository.SessionRepository;
import com.helpet.service.account.util.HttpRequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class SessionService {
    private final AccountService accountService;

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(AccountService accountService, SessionRepository sessionRepository) {
        this.accountService = accountService;
        this.sessionRepository = sessionRepository;
    }

    public Session getSession(UUID sessionId) throws NotFoundLocalizedException {
        return sessionRepository.findById(sessionId)
                                .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.SESSION_WITH_GIVEN_ID_DOES_NOT_EXIST));
    }

    public List<Session> getAccountSessions(UUID accountId) throws NotFoundLocalizedException {
        if (!accountService.accountExists(accountId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_WITH_GIVEN_ID_DOES_NOT_EXIST);
        }

        return sessionRepository.findAllByAccountIdOrderByIssuedAtDesc(accountId);
    }

    public Session getAccountSession(UUID accountId, UUID sessionId) throws NotFoundLocalizedException {
        if (!accountService.accountExists(accountId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_WITH_GIVEN_ID_DOES_NOT_EXIST);
        }

        Session session = getSession(sessionId);
        if (!Objects.equals(session.getAccountId(), accountId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_HAVE_THIS_SESSION);
        }

        return session;
    }

    public void leaveAccountSession(UUID accountId, UUID sessionId) throws NotFoundLocalizedException {
        Session session = getAccountSession(accountId, sessionId);
        sessionRepository.delete(session);
    }

    public void leaveAllAccountSessionsExceptCurrent(UUID accountId, UUID currentSessionId) throws NotFoundLocalizedException {
        for (Session session : getAccountSessions(accountId)) {
            if (!Objects.equals(session.getId(), currentSessionId)) {
                sessionRepository.delete(session);
            }
        }
    }

    public Session createSession(UUID accountId, HttpServletRequest httpRequest, Long expiresIn) {
        Session session = Session.builder()
                                 .accountId(accountId)
                                 .ip(HttpRequestUtils.getRemoteIp(httpRequest))
                                 .userAgent(HttpRequestUtils.getUserAgent(httpRequest))
                                 .expiresIn(expiresIn)
                                 .issuedAt(Instant.now())
                                 .build();

        return sessionRepository.save(session);
    }

    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }
}

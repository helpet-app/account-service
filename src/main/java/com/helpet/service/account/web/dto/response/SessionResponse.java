package com.helpet.service.account.web.dto.response;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SessionResponse {
    private UUID id;

    private UUID accountId;

    private String ip;

    private String userAgent;

    private Instant issuedAt;
}

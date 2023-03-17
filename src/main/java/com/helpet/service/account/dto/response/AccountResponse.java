package com.helpet.service.account.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class AccountResponse {
    private UUID id;

    private String username;

    private String name;

    private String email;

    private Boolean isEmailVerified;

    private Boolean isEnabled;

    private Boolean isBlocked;
}

package com.helpet.service.account.dto.request;

import lombok.Data;

@Data
public class SignUpRequest {
    private String name;

    private String email;

    private String username;

    private String password;
}
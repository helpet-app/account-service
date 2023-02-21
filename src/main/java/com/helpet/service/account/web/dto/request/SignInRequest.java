package com.helpet.service.account.web.dto.request;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;

    private String password;
}

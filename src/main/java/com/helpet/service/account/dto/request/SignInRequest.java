package com.helpet.service.account.dto.request;

import com.helpet.validation.annotation.Password;
import com.helpet.validation.annotation.Username;
import lombok.Data;

@Data
public class SignInRequest {
    @Username(message = "{validations.username.username-is-invalid}")
    private String username;

    @Password(message = "{validations.password.password-must-contain-n-characters}")
    private String password;
}

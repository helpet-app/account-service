package com.helpet.service.account.dto.request;

import com.helpet.validation.annotation.Name;
import com.helpet.validation.annotation.Password;
import com.helpet.validation.annotation.Username;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {
    @Name(message = "{validations.name.name-is-invalid}")
    private String name;

    @NotBlank(message = "{validations.not-blank.email-cannot-be-blank}")
    @Email(message = "{validations.email.email-is-invalid}")
    private String email;

    @Username(message = "{validations.username.username-is-invalid}")
    private String username;

    @Password(message = "{validations.password.password-must-contain-n-characters}")
    private String password;
}

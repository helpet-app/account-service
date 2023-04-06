package com.helpet.service.account.dto.request;

import com.helpet.validation.annotation.Password;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @Password(message = "{validations.password.password-must-contain-n-characters}")
    private String currentPassword;

    @Password(message = "{validations.password.password-must-contain-n-characters}")
    private String newPassword;
}

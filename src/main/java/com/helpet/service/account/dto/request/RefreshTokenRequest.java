package com.helpet.service.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "{validations.not-blank.refresh-token-cannot-be-blank}")
    private String refreshToken;
}

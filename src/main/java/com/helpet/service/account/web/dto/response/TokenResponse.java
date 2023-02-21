package com.helpet.service.account.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenResponse {
    private String accessToken;

    private Long accessExpiresIn;

    private String refreshToken;

    private Long refreshExpiresIn;

    @Builder.Default
    private String tokenType = "Bearer";
}

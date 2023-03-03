package com.helpet.service.account.web.dto.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String currentPassword;

    private String newPassword;
}

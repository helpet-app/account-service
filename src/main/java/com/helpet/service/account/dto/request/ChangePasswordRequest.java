package com.helpet.service.account.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String currentPassword;

    private String newPassword;
}

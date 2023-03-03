package com.helpet.service.account.web.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String currentPassword;

    private String newPassword;
}

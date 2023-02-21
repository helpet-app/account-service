package com.helpet.service.account.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum ConflictLocalizedError implements DefaultEnumLocalizedError {
    USERNAME_IS_ALREADY_TAKEN,
    EMAIL_IS_ALREADY_TAKEN;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.conflict";
    }
}

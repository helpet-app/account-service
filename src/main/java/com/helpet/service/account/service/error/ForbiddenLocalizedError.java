package com.helpet.service.account.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum ForbiddenLocalizedError implements DefaultEnumLocalizedError {
    BAD_ACCOUNT_CREDENTIALS,
    ACCOUNT_IS_DISABLED,
    ACCOUNT_IS_BLOCKED;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.forbidden";
    }
}

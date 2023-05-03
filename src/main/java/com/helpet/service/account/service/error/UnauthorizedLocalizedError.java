package com.helpet.service.account.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum UnauthorizedLocalizedError implements DefaultEnumLocalizedError {
    TOKEN_IS_INVALID;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.unauthorized";
    }
}

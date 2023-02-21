package com.helpet.service.account.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum NotFoundLocalizedError implements DefaultEnumLocalizedError {
    ACCOUNT_WITH_GIVEN_USERNAME_DOES_NOT_EXIST,
    ACCOUNT_WITH_GIVEN_ID_DOES_NOT_EXIST,
    SESSION_WITH_GIVEN_ID_DOES_NOT_EXIST,
    ACCOUNT_DOES_NOT_HAVE_THIS_SESSION;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.not-found";
    }
}

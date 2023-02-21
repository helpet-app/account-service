package com.helpet.service.account.util;

import com.helpet.exception.ForbiddenLocalizedException;
import com.helpet.service.account.service.error.ForbiddenLocalizedError;
import com.helpet.web.handler.LocalizedExceptionHandler;
import com.helpet.web.response.UnsuccessfulResponseBody;
import com.helpet.web.util.Localizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class BaseExceptionHandler extends LocalizedExceptionHandler {
    @Autowired
    public BaseExceptionHandler(Localizer localizer) {
        super(localizer);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<UnsuccessfulResponseBody> handleBadCredentialsException(Locale locale) {
        ForbiddenLocalizedException ex = new ForbiddenLocalizedException(ForbiddenLocalizedError.BAD_ACCOUNT_CREDENTIALS);
        return buildResponseEntity(ex, HttpStatus.FORBIDDEN, locale);
    }

    @ExceptionHandler(DisabledException.class)
    protected ResponseEntity<UnsuccessfulResponseBody> handleDisabledException(Locale locale) {
        ForbiddenLocalizedException ex = new ForbiddenLocalizedException(ForbiddenLocalizedError.ACCOUNT_IS_DISABLED);
        return buildResponseEntity(ex, HttpStatus.FORBIDDEN, locale);
    }

    @ExceptionHandler(LockedException.class)
    protected ResponseEntity<UnsuccessfulResponseBody> handleLockedException(Locale locale) {
        ForbiddenLocalizedException ex = new ForbiddenLocalizedException(ForbiddenLocalizedError.ACCOUNT_IS_BLOCKED);
        return buildResponseEntity(ex, HttpStatus.FORBIDDEN, locale);
    }
}

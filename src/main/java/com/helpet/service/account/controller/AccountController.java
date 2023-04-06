package com.helpet.service.account.controller;

import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.account.dto.request.ChangePasswordRequest;
import com.helpet.service.account.dto.request.UpdateAccountRequest;
import com.helpet.service.account.mapper.AccountMapper;
import com.helpet.service.account.service.AccountService;
import com.helpet.service.account.storage.model.Account;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/account")
@RestController
public class AccountController {
    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @Autowired
    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getAccount(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID accountId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Account account = accountService.getAccount(accountId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(accountMapper.map(account));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<ResponseBody> updateAccount(@RequestBody @Valid UpdateAccountRequest updateAccountRequest,
                                                      JwtAuthenticationToken jwtAuthenticationToken) {
        UUID accountId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Account account = accountService.updateAccount(accountId, updateAccountRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(accountMapper.map(account));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<ResponseBody> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest,
                                                       JwtAuthenticationToken jwtAuthenticationToken) {
        UUID accountId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        UUID sessionId = JwtPayloadExtractor.extractSessionId(jwtAuthenticationToken.getToken());
        accountService.changePassword(accountId, sessionId, changePasswordRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}

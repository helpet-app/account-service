package com.helpet.service.account.web.controller;

import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.account.service.AccountService;
import com.helpet.service.account.store.model.Account;
import com.helpet.service.account.web.mapper.AccountMapper;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/account/me")
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
}

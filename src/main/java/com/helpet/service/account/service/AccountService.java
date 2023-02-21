package com.helpet.service.account.service;

import com.helpet.exception.ConflictLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.account.service.error.ConflictLocalizedError;
import com.helpet.service.account.service.error.NotFoundLocalizedError;
import com.helpet.service.account.store.model.Account;
import com.helpet.service.account.store.model.Role;
import com.helpet.service.account.store.repository.AccountRepository;
import com.helpet.service.account.web.dto.request.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account getAccount(UUID id) throws NotFoundLocalizedException {
        return accountRepository.findById(id)
                                .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_WITH_GIVEN_ID_DOES_NOT_EXIST));
    }

    public Account getAccountByUsername(String username) throws NotFoundLocalizedException {
        return accountRepository.findAccountByUsername(username)
                                .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_WITH_GIVEN_USERNAME_DOES_NOT_EXIST));
    }

    public boolean accountExists(UUID id) {
        return accountRepository.existsById(id);
    }

    public boolean accountExistsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    public boolean accountExistsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    public Account createAccount(SignUpRequest signUpInfo) throws ConflictLocalizedException {
        if (accountExistsByUsername(signUpInfo.getUsername())) {
            throw new ConflictLocalizedException(ConflictLocalizedError.USERNAME_IS_ALREADY_TAKEN);
        }

        if (accountExistsByEmail(signUpInfo.getEmail())) {
            throw new ConflictLocalizedException(ConflictLocalizedError.EMAIL_IS_ALREADY_TAKEN);
        }

        Account newAccount = Account.builder()
                                    .name(signUpInfo.getName())
                                    .email(signUpInfo.getEmail())
                                    .username(signUpInfo.getUsername())
                                    .password(passwordEncoder.encode(signUpInfo.getPassword()))
                                    .roles(Set.of(Role.USER))
                                    .build();

        return accountRepository.save(newAccount);
    }
}

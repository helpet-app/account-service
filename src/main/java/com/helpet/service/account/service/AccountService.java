package com.helpet.service.account.service;

import com.helpet.exception.ConflictLocalizedException;
import com.helpet.exception.ForbiddenLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.account.service.error.ConflictLocalizedError;
import com.helpet.service.account.service.error.ForbiddenLocalizedError;
import com.helpet.service.account.service.error.NotFoundLocalizedError;
import com.helpet.service.account.store.model.Account;
import com.helpet.service.account.store.model.Role;
import com.helpet.service.account.store.repository.AccountRepository;
import com.helpet.service.account.dto.request.SignUpRequest;
import com.helpet.service.account.dto.request.ChangePasswordRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final SessionService sessionService;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, @Lazy SessionService sessionService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionService = sessionService;
    }

    public Account getAccount(UUID id) throws NotFoundLocalizedException {
        return accountRepository.findById(id)
                                .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_WITH_GIVEN_ID_DOES_NOT_EXIST));
    }

    public Account getAccountByUsername(String username) throws NotFoundLocalizedException {
        return accountRepository.findAccountByUsernameIgnoreCase(username)
                                .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_WITH_GIVEN_USERNAME_DOES_NOT_EXIST));
    }

    public boolean accountExists(UUID id) {
        return accountRepository.existsById(id);
    }

    public boolean accountExistsByUsername(String username) {
        return accountRepository.existsByUsernameIgnoreCase(username);
    }

    public boolean accountExistsByEmail(String email) {
        return accountRepository.existsByEmailIgnoreCase(email);
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
                                    .email(signUpInfo.getEmail().toLowerCase())
                                    .username(signUpInfo.getUsername().toLowerCase())
                                    .password(passwordEncoder.encode(signUpInfo.getPassword()))
                                    .roles(Set.of(Role.USER.name()))
                                    .build();

        return accountRepository.save(newAccount);
    }

    public void changePassword(UUID accountId,
                               UUID sessionId,
                               ChangePasswordRequest changePasswordInfo) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        Account account = getAccount(accountId);

        if (!passwordEncoder.matches(changePasswordInfo.getCurrentPassword(), account.getPassword())) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.BAD_ACCOUNT_CREDENTIALS);
        }

        account.setPassword(passwordEncoder.encode(changePasswordInfo.getNewPassword()));

        accountRepository.save(account);

        sessionService.leaveAllAccountSessionsExceptCurrent(accountId, sessionId);
    }
}

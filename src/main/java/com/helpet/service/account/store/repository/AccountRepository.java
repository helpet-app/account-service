package com.helpet.service.account.store.repository;

import com.helpet.service.account.store.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findAccountByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}

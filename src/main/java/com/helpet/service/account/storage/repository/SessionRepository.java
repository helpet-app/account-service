package com.helpet.service.account.storage.repository;

import com.helpet.service.account.storage.model.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends CrudRepository<Session, UUID> {
    List<Session> findAllByAccountIdOrderByIssuedAtDesc(UUID accountId);
}

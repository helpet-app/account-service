package com.helpet.service.account.store.repository;

import com.helpet.service.account.store.model.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends CrudRepository<Session, UUID> {
    List<Session> findAllByAccountId(UUID accountId);
}

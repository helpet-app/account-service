package com.helpet.service.account.storage.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RedisAtomicRepository {
    private final RedisConnectionFactory redisConnectionFactory;

    private final String key = "Atomics:";

    @Autowired
    public RedisAtomicRepository(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    public RedisAtomicInteger getAtomic(UUID id) {
        return new RedisAtomicInteger(key + id, redisConnectionFactory);
    }
}

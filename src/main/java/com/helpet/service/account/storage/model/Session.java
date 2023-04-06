package com.helpet.service.account.storage.model;

import com.helpet.service.account.util.InstantToStringConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Indexed
    private UUID accountId;

    @NotNull
    private String ip;

    private String userAgent;

    @NotNull
    @TimeToLive
    private Long expiresIn;

    @Convert(converter = InstantToStringConverter.class)
    @NotNull
    private Instant issuedAt;
}
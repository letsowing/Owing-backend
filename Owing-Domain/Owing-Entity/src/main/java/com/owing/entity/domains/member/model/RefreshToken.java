package com.owing.entity.domains.member.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("refreshToken")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    private String token;

    private String memberId;

    @TimeToLive
    private Long ttl;

    @Builder
    private RefreshToken(String token, String memberId, Long ttl) {
        this.token = token;
        this.memberId = memberId;
        this.ttl = ttl;
    }
}

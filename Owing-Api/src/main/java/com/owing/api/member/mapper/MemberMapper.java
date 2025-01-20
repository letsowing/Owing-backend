package com.owing.api.member.mapper;

import static com.owing.common.constant.TokenConst.*;

import com.google.api.client.json.webtoken.JsonWebToken;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Mapper
@RequiredArgsConstructor
public class MemberMapper {

    private final PasswordEncoder passwordEncoder;

    public Member toEntity(JsonWebToken.Payload payload, OauthProvider provider) {
        return Member.builder()
                .email(payload.get(GOOGLE_CLAIM_EMAIL).toString())
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .provider(provider)
                .name(payload.get(GOOGLE_CLAIM_NAME).toString())
                .nickname(payload.get(GOOGLE_CLAIM_NAME).toString())
                .profileUrl(payload.get(GOOGLE_CLAIM_PICTURE).toString())
                .build();
    }
}

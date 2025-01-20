package com.owing.api.auth.service;

import com.owing.api.auth.model.dto.response.TokenResponse;
import com.owing.api.auth.model.mapper.AuthMapper;
import com.owing.api.auth.service.helper.GoogleOauthHelper;
import com.owing.common.util.JwtUtils;
import com.owing.api.member.mapper.MemberMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.member.adapter.MemberAdapter;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.RefreshToken;
import com.owing.entity.domains.member.service.MemberDomainService;
import com.owing.entity.domains.member.service.RefreshTokenDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import static com.google.api.client.json.webtoken.JsonWebToken.*;
import static com.owing.common.constant.TokenConst.*;
import static com.owing.entity.domains.member.model.OauthProvider.*;

@UseCase
@RequiredArgsConstructor
public class GoogleOauthLoginUseCase {

    private final MemberDomainService memberDomainService;
    private final RefreshTokenDomainService refreshTokenDomainService;
    private final MemberAdapter memberAdapter;
    private final MemberMapper memberMapper;
    private final AuthMapper authMapper;
    private final GoogleOauthHelper googleOauthHelper;
    private final JwtUtils jwtUtils;

    @Transactional
    public TokenResponse execute(String idToken) {
        Payload payload = validateGoogleIdToken(idToken);
        Member member = memberAdapter.findByEmailAndProvider(payload.get(GOOGLE_CLAIM_EMAIL).toString(), GOOGLE)
                .orElseGet(() -> memberDomainService.createMember(memberMapper.toEntity(payload, GOOGLE)));
        return createTokens(member);
    }

    private Payload validateGoogleIdToken(String idToken) {
        return googleOauthHelper.verifyGoogleIdToken(idToken);
    }

    private TokenResponse createTokens(Member member) {
        RefreshToken refreshToken = refreshTokenDomainService.createRefreshToken(jwtUtils.getRefreshToken(member.getId()));
        return authMapper.toTokenResponse(BEARER_TYPE, jwtUtils.generateAccessToken(member), refreshToken.getToken());
    }
}

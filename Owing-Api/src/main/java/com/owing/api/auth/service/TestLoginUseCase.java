package com.owing.api.auth.service;

import com.owing.api.auth.model.dto.response.TokenResponse;
import com.owing.api.auth.model.mapper.AuthMapper;
import com.owing.api.auth.service.helper.GoogleOauthHelper;
import com.owing.api.common.util.JwtUtils;
import com.owing.api.member.mapper.MemberMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.member.adapter.MemberAdapter;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.RefreshToken;
import com.owing.entity.domains.member.service.MemberDomainService;
import com.owing.entity.domains.member.service.RefreshTokenDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static com.google.api.client.json.webtoken.JsonWebToken.Payload;
import static com.owing.api.common.constant.TokenConst.BEARER_TYPE;
import static com.owing.entity.domains.member.model.OauthProvider.GOOGLE;

@UseCase
@RequiredArgsConstructor
public class TestLoginUseCase {

    private final MemberDomainService memberDomainService;
    private final RefreshTokenDomainService refreshTokenDomainService;
    private final MemberAdapter memberAdapter;
    private final AuthMapper authMapper;
    private final GoogleOauthHelper googleOauthHelper;
    private final JwtUtils jwtUtils;

    @Transactional("jpaTransactionManager")
    public TokenResponse execute() {
        String testEmail = "test@test.com";
        Member member = memberAdapter.findByEmailAndProvider(testEmail, GOOGLE)
                .orElseGet(() -> memberDomainService.createMember(new Member(
                        null,
                        testEmail,
                        testEmail,
                        "test user",
                        "test user",
                        null,
                        null,
                        GOOGLE
                )));
        return createTokens(member);
    }

    private TokenResponse createTokens(Member member) {
        RefreshToken refreshToken = refreshTokenDomainService.createRefreshToken(jwtUtils.getRefreshToken(member.getId()));
        return authMapper.toTokenResponse(BEARER_TYPE, jwtUtils.generateAccessToken(member), refreshToken.getToken());
    }
}

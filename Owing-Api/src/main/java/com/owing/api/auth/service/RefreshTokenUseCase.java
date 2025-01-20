package com.owing.api.auth.service;

import com.owing.api.auth.model.dto.response.TokenResponse;
import com.owing.api.auth.model.mapper.AuthMapper;
import com.owing.common.util.JwtUtils;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.member.adapter.MemberAdapter;
import com.owing.entity.domains.member.adapter.RefreshTokenAdapter;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.RefreshToken;
import com.owing.entity.domains.member.service.RefreshTokenDomainService;
import lombok.RequiredArgsConstructor;

import static com.owing.common.constant.TokenConst.BEARER_TYPE;

@UseCase
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final RefreshTokenDomainService refreshTokenDomainService;
    private final RefreshTokenAdapter refreshTokenAdapter;
    private final MemberAdapter memberAdapter;
    private final AuthMapper authMapper;
    private final JwtUtils jwtUtils;

    public TokenResponse execute(String refreshToken) {
        RefreshToken existedRefreshToken = validateRefreshToken(refreshToken);
        Member member = memberAdapter.findById(Long.parseLong(existedRefreshToken.getMemberId()));
        refreshTokenDomainService.deleteRefreshToken(existedRefreshToken);
        return createTokens(member);
    }

    private RefreshToken validateRefreshToken(String refreshToken) {
        RefreshToken existedRefreshToken = refreshTokenAdapter.findById(refreshToken);
        jwtUtils.validateToken(refreshToken);
        return existedRefreshToken;
    }

    private TokenResponse createTokens(Member member) {
        RefreshToken refreshToken = refreshTokenDomainService.createRefreshToken(jwtUtils.getRefreshToken(member.getId()));
        return authMapper.toTokenResponse(BEARER_TYPE, jwtUtils.generateAccessToken(member), refreshToken.getToken());
    }
}

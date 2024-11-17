package com.owing.entity.domains.member.service;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.member.model.RefreshToken;
import com.owing.entity.domains.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class RefreshTokenDomainService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    public void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }
}

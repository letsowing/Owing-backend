package com.owing.api.auth.service;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.member.adapter.RefreshTokenAdapter;
import com.owing.entity.domains.member.model.RefreshToken;
import com.owing.entity.domains.member.service.RefreshTokenDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class LogoutUseCase {

    private final RefreshTokenDomainService refreshTokenDomainService;
    private final RefreshTokenAdapter refreshTokenAdapter;

    public void execute(String refreshToken) {
        RefreshToken existedRefreshToken = refreshTokenAdapter.findById(refreshToken);
        refreshTokenDomainService.deleteRefreshToken(existedRefreshToken);
    }
}

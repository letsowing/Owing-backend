package com.owing.api.auth.service;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.member.adaptor.RefreshTokenAdaptor;
import com.owing.entity.domains.member.model.RefreshToken;
import com.owing.entity.domains.member.service.RefreshTokenDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class LogoutUseCase {

    private final RefreshTokenDomainService refreshTokenDomainService;
    private final RefreshTokenAdaptor refreshTokenAdaptor;

    public void execute(String refreshToken) {
        RefreshToken existedRefreshToken = refreshTokenAdaptor.findById(refreshToken);
        refreshTokenDomainService.deleteRefreshToken(existedRefreshToken);
    }
}

package com.owing.entity.domains.member.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.domains.member.error.exception.RefreshTokenException;
import com.owing.entity.domains.member.model.RefreshToken;
import com.owing.entity.domains.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;

import static com.owing.entity.domains.member.error.RefreshTokenErrorCode.*;

@Adaptor
@RequiredArgsConstructor
public class RefreshTokenAdapter {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findById(String refreshToken) {
        return refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> RefreshTokenException.of(REFRESH_TOKEN_NOT_FOUND));
    }
}

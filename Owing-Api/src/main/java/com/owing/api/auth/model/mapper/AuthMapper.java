package com.owing.api.auth.model.mapper;

import com.owing.api.auth.model.dto.response.TokenResponse;
import com.owing.common.annotation.Mapper;
import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class AuthMapper {

    public TokenResponse toTokenResponse(String grantType, String accessToken, String refreshToken) {
        return new TokenResponse(grantType, accessToken, refreshToken);
    }
}

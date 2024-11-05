package com.owing.api.auth.model.dto.response;

public record TokenResponse(
        String grantType,
        String accessToken,
        String refreshToken
) {
}

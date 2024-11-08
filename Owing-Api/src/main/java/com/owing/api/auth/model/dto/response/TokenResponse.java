package com.owing.api.auth.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record TokenResponse(
        String grantType,
        String accessToken,
        @JsonIgnore
        String refreshToken
) {
}

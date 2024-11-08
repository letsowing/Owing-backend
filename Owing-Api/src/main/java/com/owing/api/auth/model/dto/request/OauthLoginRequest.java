package com.owing.api.auth.model.dto.request;

import com.owing.entity.domains.member.model.OauthProvider;
import jakarta.validation.constraints.NotNull;

public record OauthLoginRequest(
        @NotNull
        String idToken,
        @NotNull
        OauthProvider provider
) {
}

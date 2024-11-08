package com.owing.api.auth.controller;

import com.owing.api.auth.model.dto.request.OauthLoginRequest;
import com.owing.api.auth.model.dto.response.TokenResponse;
import com.owing.api.auth.service.GoogleOauthLoginUseCase;
import com.owing.api.auth.service.LogoutUseCase;
import com.owing.api.auth.service.RefreshTokenUseCase;
import com.owing.api.auth.service.helper.CookieHelper;
import com.owing.entity.domains.member.model.OauthProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.owing.api.common.constant.TokenConst.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleOauthLoginUseCase googleOauthLoginUsecase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;
    private final CookieHelper cookieHelper;

    @PostMapping("/oauth")
    ResponseEntity<TokenResponse> oauthLogin(@Valid @RequestBody OauthLoginRequest oauthLoginRequest) {
        if (OauthProvider.isGoogle(oauthLoginRequest.provider())) {
            TokenResponse tokenResponse = googleOauthLoginUsecase.execute(oauthLoginRequest.idToken());
            return ResponseEntity.ok()
                    .headers(cookieHelper.getRefreshTokenCookie(tokenResponse.refreshToken()))
                    .body(tokenResponse);
        }
        return ResponseEntity.status(UNAUTHORIZED).build();
    }

    @PostMapping("/refresh")
    ResponseEntity<TokenResponse> refreshTokens(@CookieValue(value = REFRESH_TOKEN, required = false) String refreshToken) {
        TokenResponse tokenResponse = refreshTokenUseCase.execute(refreshToken);
        return ResponseEntity.ok()
                .headers(cookieHelper.getRefreshTokenCookie(tokenResponse.refreshToken()))
                .body(tokenResponse);
    }

    @PostMapping("/logout")
    ResponseEntity<?> logout(@CookieValue(value = REFRESH_TOKEN, required = false) String refreshToken) {
        logoutUseCase.execute(refreshToken);
        return ResponseEntity.ok()
                .headers(cookieHelper.deleteCookie())
                .build();
    }
}

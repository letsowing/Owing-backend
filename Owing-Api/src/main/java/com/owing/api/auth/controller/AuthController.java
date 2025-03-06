package com.owing.api.auth.controller;

import com.owing.api.auth.model.dto.request.OauthLoginRequest;
import com.owing.api.auth.model.dto.response.TokenResponse;
import com.owing.api.auth.service.GoogleOauthLoginUseCase;
import com.owing.api.auth.service.LogoutUseCase;
import com.owing.api.auth.service.RefreshTokenUseCase;
import com.owing.api.auth.service.TestLoginUseCase;
import com.owing.api.auth.service.helper.CookieHelper;
import com.owing.entity.domains.member.model.OauthProvider;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.owing.common.constant.TokenConst.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleOauthLoginUseCase googleOauthLoginUsecase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;
    private final CookieHelper cookieHelper;
    private final TestLoginUseCase testLoginUseCase;

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

    @Profile({"local", "dev"})
    @Operation(
            summary = "개발용 로그인",
            description = "개발용 로그인입니다. 이메일이 test@test.com인 유저를 조회합니다."
    )
    @GetMapping("/v1/test-login")
    public ResponseEntity<TokenResponse> testLogin() {
        TokenResponse tokenResponse = testLoginUseCase.execute();
        return ResponseEntity.ok()
                .headers(cookieHelper.getRefreshTokenCookie(tokenResponse.refreshToken()))
                .body(tokenResponse);
    }
}

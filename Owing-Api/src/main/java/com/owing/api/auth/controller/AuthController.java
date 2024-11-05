package com.owing.api.auth.controller;

import com.owing.api.auth.model.dto.request.OauthLoginRequest;
import com.owing.api.auth.model.dto.request.RefreshTokenRequest;
import com.owing.api.auth.model.dto.response.TokenResponse;
import com.owing.api.auth.service.GoogleOauthLoginUseCase;
import com.owing.api.auth.service.RefreshTokenUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.owing.entity.domains.member.model.OauthProvider.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleOauthLoginUseCase googleOauthLoginUsecase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/oauth")
    ResponseEntity<TokenResponse> oauthLogin(@Valid @RequestBody OauthLoginRequest oauthLoginRequest) {
        if (oauthLoginRequest.provider() == GOOGLE) {
            return ResponseEntity.ok(googleOauthLoginUsecase.execute(oauthLoginRequest.idToken()));
        }
        return ResponseEntity.status(UNAUTHORIZED).build();
    }

    @PostMapping("/refresh")
    ResponseEntity<TokenResponse> refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(refreshTokenUseCase.execute(refreshTokenRequest));
    }
}

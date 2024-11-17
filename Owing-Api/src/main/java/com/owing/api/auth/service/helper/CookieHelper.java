package com.owing.api.auth.service.helper;

import com.owing.api.auth.model.dto.response.TokenResponse;
import com.owing.common.annotation.Helper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import static com.owing.api.common.constant.TokenConst.*;

@Helper
public class CookieHelper {

    public HttpHeaders getRefreshTokenCookie(String refreshToken) {
        ResponseCookie responseCookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .path(COOKIE_PATH)
                .maxAge(REFRESH_TOKEN_EXPIRE_TIME)
                .sameSite(SAME_SITE_NONE)
                .httpOnly(true)
                .secure(true)
                .build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, responseCookie.toString());
        return httpHeaders;
    }

    public HttpHeaders deleteCookie() {
        ResponseCookie responseCookie = ResponseCookie.from(REFRESH_TOKEN, null)
                .path(COOKIE_PATH)
                .maxAge(ZERO_TIME)
                .sameSite(SAME_SITE_NONE)
                .httpOnly(true)
                .secure(true)
                .build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, responseCookie.toString());
        return httpHeaders;
    }
}

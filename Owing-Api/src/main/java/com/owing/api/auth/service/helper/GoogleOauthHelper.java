package com.owing.api.auth.service.helper;

import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.auth.oauth2.TokenVerifier;
import com.owing.api.auth.error.exception.AuthInvalidTokenException;
import com.owing.api.common.properties.GoogleOauthProperties;
import com.owing.common.annotation.Helper;
import lombok.RequiredArgsConstructor;

import static com.owing.api.auth.error.AuthErrorCode.*;

@Helper
@RequiredArgsConstructor
public class GoogleOauthHelper {

    private final GoogleOauthProperties googleOauthProperties;

    public JsonWebToken.Payload verifyGoogleIdToken(String idToken) {
        try {
            TokenVerifier tokenVerifier = TokenVerifier.newBuilder()
                    .setAudience(googleOauthProperties.audience())
                    .build();
            return tokenVerifier.verify(idToken).getPayload();
        } catch (TokenVerifier.VerificationException e) {
            throw AuthInvalidTokenException.of(INVALID_ID_TOKEN);
        }
    }
}

package com.owing.api.common;

import com.owing.api.auth.error.exception.AuthInvalidTokenException;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.json.JSONObject;

import java.security.Key;
import java.util.Date;

import static com.owing.api.auth.error.AuthErrorCode.*;
import static com.owing.api.common.constant.TokenConst.*;

@Component
public class JwtUtils {

    private final Key key;

    public JwtUtils(JwtProperties jwtProperties) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Member member) {
        long now = (new Date()).getTime();
        return Jwts.builder()
                .setSubject(member.getId().toString())
                .claim(CLAIM_NICKNAME, member.getNickname())
                .claim(CLAIM_PROFILE_URL, member.getProfileUrl())
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public RefreshToken getRefreshToken(Long memberId) {
        return RefreshToken.builder()
                .token(generateRefreshToken())
                .memberId(memberId.toString())
                .ttl(REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }

    public Long parseAccessToken(String accessToken) {
        return Long.parseLong(validateToken(accessToken).getSubject());
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw AuthInvalidTokenException.of(UNAUTHORIZED_ACCESS);
        } catch (Exception e) {
            throw AuthInvalidTokenException.of(INVALID_TOKEN);
        }
    }

    private String generateRefreshToken() {
        long now = (new Date()).getTime();
        return Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

}

package com.owing.api.filter;

import static com.owing.api.auth.error.AuthErrorCode.*;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.owing.api.auth.error.AuthErrorCode;
import com.owing.api.auth.error.exception.AuthInvalidTokenException;
import com.owing.api.common.JwtUtils;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtUtils jwtUtils;

	// Jwt Provier 주입
	public JwtAuthenticationFilter(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");

		String accessToken = "";

		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw AuthInvalidTokenException.of(INVALID_AUTH_TOKEN);
		}

		try{
			accessToken = authHeader.split(" ")[1].trim();
		} catch (Exception e) {
			throw AuthInvalidTokenException.of(INVALID_AUTH_TOKEN);
		}

		try{
			jwtUtils.validateToken(accessToken);
		} catch (Exception e){
			throw AuthInvalidTokenException.of(INVALID_REFRESH_TOKEN);
		}

		Long userId = jwtUtils.parseAccessToken(accessToken);

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			userId,
			null, List.of(new SimpleGrantedAuthority("DEFAULT_ROLE")));

		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}
}

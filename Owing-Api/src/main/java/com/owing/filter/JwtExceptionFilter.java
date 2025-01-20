package com.owing.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.api.auth.error.AuthErrorCode;
import com.owing.common.error.dto.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			setErrorResponse(response, AuthErrorCode.EXPIRE_ACCESS_TOKEN);
		} catch (Exception e) {
			setErrorResponse(response, AuthErrorCode.INVALID_TOKEN);
		}
	}

	private void setErrorResponse(HttpServletResponse response, AuthErrorCode errorCode) {
		log.error("Jwt 검증 filter에서 에러 탐지");

		ObjectMapper objectMapper = new ObjectMapper();
		response.setStatus(errorCode.getStatus().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");

		try {
			response.getWriter().write(objectMapper.writeValueAsString(
				ErrorResponse.of(errorCode,errorCode.getMessage())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
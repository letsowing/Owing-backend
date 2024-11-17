package com.owing.api.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.member.service.DashboardUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final DashboardUseCase dashboardUseCase;

	@GetMapping("/v1/dashbaord")
	public ResponseEntity<?> getDashboard(){
		return ResponseEntity.ok(dashboardUseCase.execute());
	}
}

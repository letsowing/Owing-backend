package com.owing.api.universe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.service.CreateUniverseUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/universes")
@RequiredArgsConstructor
public class UniverseController {

	private final CreateUniverseUseCase createUniverseUseCase;

	@PostMapping
	public ResponseEntity<UniverseShortInfoResponse> createUniverse(@RequestBody AddUniverseRequest addUniverseRequest) {
		return ResponseEntity.ok(createUniverseUseCase.execute(addUniverseRequest));
	}


}

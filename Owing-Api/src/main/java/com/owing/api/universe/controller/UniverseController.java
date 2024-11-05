package com.owing.api.universe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.service.CreateUniverseUseCase;
import com.owing.api.universe.service.ReadUniverseUseCase;
import com.owing.api.universe.service.UpdateUniverseUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/universes")
@RequiredArgsConstructor
public class UniverseController {

	private final CreateUniverseUseCase createUniverseUseCase;
	private final UpdateUniverseUseCase updateUniverseUseCase;
	private final ReadUniverseUseCase readUniverseUseCase;

	@PostMapping
	public ResponseEntity<UniverseShortInfoResponse> createUniverse(@RequestBody AddUniverseRequest addUniverseRequest) {
		return ResponseEntity.ok(createUniverseUseCase.execute(addUniverseRequest));
	}

	@PutMapping("/{universeId}")
	public ResponseEntity<UniverseShortInfoResponse> updateUniverse(
		@PathVariable Long universeId,
		@RequestBody UpdateUniverseRequest updateUniverseRequest) {
		return ResponseEntity.ok(updateUniverseUseCase.execute(universeId, updateUniverseRequest));
	}

	@GetMapping("/{universeId}")
	public ResponseEntity<UniverseShortInfoResponse> readUniverse(@PathVariable Long universeId) {
		return ResponseEntity.ok(readUniverseUseCase.execute(universeId));
	}



}

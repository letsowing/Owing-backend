package com.owing.api.cast.controller;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.request.CreateConnectionRequest;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.dto.response.CastRelationshipInfoResponse;
import com.owing.api.cast.service.CreateCastUseCase;
import com.owing.api.cast.service.CreateConnectionUseCase;
import com.owing.api.cast.service.ReadCastUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cast")
@RequiredArgsConstructor
public class CastController {

    private final CreateCastUseCase createCastUseCase;
    private final CreateConnectionUseCase createConnectionUseCase;
    private final ReadCastUseCase readCastUseCase;

    @GetMapping("/{castId}")
    public ResponseEntity<CastInfoResponse> getCast(@PathVariable Long castId) {
        return ResponseEntity.ok(readCastUseCase.execute(castId));
    }

    @PostMapping
    public ResponseEntity<CastInfoResponse> createCast(@RequestBody CreateCastRequest createCastRequest) {
        return ResponseEntity.ok(createCastUseCase.execute(createCastRequest));
    }

    @PostMapping("/relationships")
    public ResponseEntity<CastRelationshipInfoResponse> createRelationship(@RequestBody CreateConnectionRequest createConnectionRequest) {
        return ResponseEntity.ok(createConnectionUseCase.execute(createConnectionRequest));
    }
}

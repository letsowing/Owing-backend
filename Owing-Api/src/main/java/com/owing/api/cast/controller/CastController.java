package com.owing.api.cast.controller;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.request.CreateConnectionRequest;
import com.owing.api.cast.model.dto.request.UpdateCastCoordinateRequest;
import com.owing.api.cast.model.dto.request.UpdateCastInfoRequest;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.dto.response.CastRelationshipInfoResponse;
import com.owing.api.cast.service.*;
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
    private final UpdateCastUseCase updateCastUseCase;
    private final DeleteCastUseCase deleteCastUseCase;

    @GetMapping("/{castId}")
    public ResponseEntity<CastInfoResponse> getCast(@PathVariable Long castId) {
        return ResponseEntity.ok(readCastUseCase.execute(castId));
    }

    @PutMapping("/{castId}")
    public ResponseEntity<Void> updateCastInfo(@PathVariable Long castId, @RequestBody UpdateCastInfoRequest updateCastInfoRequest) {
        updateCastUseCase.executeUpdateInfo(castId, updateCastInfoRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{castId}")
    public ResponseEntity<Void> updateCastCoordinate(@PathVariable Long castId, @RequestBody UpdateCastCoordinateRequest updateCastCoordinateRequest) {
        updateCastUseCase.executeUpdateCoordinate(castId, updateCastCoordinateRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<CastInfoResponse> createCast(@RequestBody CreateCastRequest createCastRequest) {
        return ResponseEntity.ok(createCastUseCase.execute(createCastRequest));
    }

    @PostMapping("/relationships")
    public ResponseEntity<CastRelationshipInfoResponse> createRelationship(@RequestBody CreateConnectionRequest createConnectionRequest) {
        return ResponseEntity.ok(createConnectionUseCase.execute(createConnectionRequest));
    }

    @DeleteMapping("/{castId}")
    public ResponseEntity<Void> deleteCast(@PathVariable Long castId) {
        deleteCastUseCase.execute(castId);
        return ResponseEntity.ok().build();
    }
}

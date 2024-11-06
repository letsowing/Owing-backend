package com.owing.api.cast.controller;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.request.CreateConnectionRequest;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.dto.response.CastRelationshipInfoResponse;
import com.owing.api.cast.service.CreateCastUserCase;
import com.owing.api.cast.service.CreateConnectionUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cast")
@RequiredArgsConstructor
public class CastController {

    private final CreateCastUserCase createCastUserCase;
    private final CreateConnectionUserCase createConnectionUserCase;

    @PostMapping
    public ResponseEntity<CastInfoResponse> createCast(@RequestBody CreateCastRequest createCastRequest) {
        return ResponseEntity.ok(createCastUserCase.execute(createCastRequest));
    }

    @PostMapping("/relationships")
    public ResponseEntity<CastRelationshipInfoResponse> createRelationship(@RequestBody CreateConnectionRequest createConnectionRequest) {
        return ResponseEntity.ok(createConnectionUserCase.execute(createConnectionRequest));
    }
}

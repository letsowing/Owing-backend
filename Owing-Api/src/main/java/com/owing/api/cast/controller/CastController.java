package com.owing.api.cast.controller;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.service.CreateCastUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cast")
@RequiredArgsConstructor
public class CastController {

    private final CreateCastUserCase createCastUserCase;

    @PostMapping
    public ResponseEntity<CastInfoResponse> createCast(@RequestBody CreateCastRequest createCastRequest) {
        return ResponseEntity.ok(createCastUserCase.execute(createCastRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CastInfoResponse> createCast(@PathVariable Long id) {
        return ResponseEntity.ok(createCastUserCase.executeTemp(id));
    }
}

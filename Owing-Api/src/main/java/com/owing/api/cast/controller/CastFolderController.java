package com.owing.api.cast.controller;

import com.owing.api.cast.model.dto.request.CreateCastFolderRequest;
import com.owing.api.cast.model.dto.response.CastFolderInfoResponse;
import com.owing.api.cast.service.CreateCastFolderUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cast/folders")
@RequiredArgsConstructor
public class CastFolderController {

    private final CreateCastFolderUserCase createCastFolderUserCase;

    @PostMapping
    public ResponseEntity<CastFolderInfoResponse> createCastFolder(@RequestBody CreateCastFolderRequest createCastFolderRequest) {
        return ResponseEntity.ok(createCastFolderUserCase.execute(createCastFolderRequest));
    }
}

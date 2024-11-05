package com.owing.api.cast.controller;

import com.owing.api.cast.model.dto.request.CreateCastFolderRequest;
import com.owing.api.cast.service.CreateCastFolderUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cast/folders")
@RequiredArgsConstructor
public class CastFolderController {

    private final CreateCastFolderUserCase createCastFolderUserCase;

    @PostMapping
    public ResponseEntity createCastFolder(@RequestBody CreateCastFolderRequest createCastFolderRequest) {
        createCastFolderUserCase.execute(createCastFolderRequest);

        return ResponseEntity.ok().build();
    }
}

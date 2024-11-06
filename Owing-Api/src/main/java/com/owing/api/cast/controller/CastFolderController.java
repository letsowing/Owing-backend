package com.owing.api.cast.controller;

import com.owing.api.cast.model.dto.request.CreateCastFolderRequest;
import com.owing.api.cast.model.dto.response.CastFolderInfoResponse;
import com.owing.api.cast.model.dto.response.CastFolderResponse;
import com.owing.api.cast.service.CreateCastFolderUserCase;
import com.owing.api.cast.service.ReadCastFolderUserCase;
import com.owing.node.folder.cast.model.CastFolderNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cast/folders")
@RequiredArgsConstructor
public class CastFolderController {

    private final CreateCastFolderUserCase createCastFolderUserCase;
    private final ReadCastFolderUserCase readCastFolderUserCase;

    @PostMapping
    public ResponseEntity<CastFolderInfoResponse> createCastFolder(@RequestBody CreateCastFolderRequest createCastFolderRequest) {
        return ResponseEntity.ok(createCastFolderUserCase.execute(createCastFolderRequest));
    }

    @GetMapping
    public ResponseEntity<List<CastFolderResponse>> getCastFolderList(@RequestParam Long projectId) {
        return ResponseEntity.ok(readCastFolderUserCase.execute(projectId));
    }
}

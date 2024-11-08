package com.owing.api.dnd.base.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.base.service.UpdateDndUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DndController<A, U, P>{

    protected final CreateDndUseCase<?, A> createDndUseCase;
    protected final ReadDndUseCase<?, ?> readDndUseCase;
    protected final DeleteDndUseCase deleteDndUseCase;
    protected final UpdateDndUseCase<?, U, P> updateDndUseCase;

    @GetMapping("/{dndId}")
    public ResponseEntity<?> getDnd(@PathVariable Long dndId) {
        return ResponseEntity.ok(readDndUseCase.executeRetrieve(dndId));
    }

    @GetMapping
    public ResponseEntity<?> getDndList(@RequestParam Long parentId) {
        return ResponseEntity.ok(readDndUseCase.executeList(parentId));
    }

    @PostMapping
    public ResponseEntity<?> createDnd(@RequestBody A addDndRequest) {
        return ResponseEntity.ok(createDndUseCase.execute(addDndRequest));
    }

    @PutMapping("/{dndId}")
    public ResponseEntity<?> updateDnd(@PathVariable Long dndId, @RequestBody U updateDndRequest) {
        return ResponseEntity.ok(updateDndUseCase.execute(dndId, updateDndRequest));
    }

    @PatchMapping("/{dndId}")
    public ResponseEntity<?> updateDndPosition(@PathVariable Long dndId, @RequestBody P updateDndPositionRequest) {
        return ResponseEntity.ok(updateDndUseCase.executeUpdatePosition(dndId, updateDndPositionRequest));
    }

    @DeleteMapping("/{dndId}")
    public ResponseEntity<Void> deleteDnd(@PathVariable Long dndId) {
        deleteDndUseCase.execute(dndId);
        return ResponseEntity.ok().build();
    }
}

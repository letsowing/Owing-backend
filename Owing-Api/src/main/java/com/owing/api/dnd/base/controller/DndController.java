package com.owing.api.dnd.base.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.base.service.UpdateDndUseCase;

public abstract class DndController<A, U, P>{

    protected abstract CreateDndUseCase<?, A> createDndUseCase();
    protected abstract ReadDndUseCase<?, ?> readDndUseCase();
    protected abstract DeleteDndUseCase deleteDndUseCase();
    protected abstract UpdateDndUseCase<U, P> updateDndUseCase();

    @GetMapping("/{dndId}")
    public ResponseEntity<?> getDnd(@PathVariable Long dndId) {
        return ResponseEntity.ok(readDndUseCase().executeRetrieve(dndId));
    }

    @GetMapping
    public ResponseEntity<?> getDndList(@RequestParam Long parentId) {
        return ResponseEntity.ok(readDndUseCase().executeList(parentId));
    }

    @PostMapping("/dnd")
    public ResponseEntity<?> createDnd(@RequestBody A addDndRequest) {
        return ResponseEntity.ok(createDndUseCase().execute(addDndRequest));
    }

    @PatchMapping("/{dndId}/title")
    public ResponseEntity<Void> updateDndTitle(@PathVariable Long dndId, @RequestBody U updateDndRequest) {
        updateDndUseCase().executeUpdateTitle(dndId, updateDndRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{dndId}/position")
    public ResponseEntity<Void> updateDndPosition(@PathVariable Long dndId, @RequestBody P updateDndPositionRequest) {
        updateDndUseCase().executeUpdatePosition(dndId, updateDndPositionRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{dndId}")
    public ResponseEntity<Void> deleteDnd(@PathVariable Long dndId) {
        deleteDndUseCase().execute(dndId);
        return ResponseEntity.noContent().build();
    }
}

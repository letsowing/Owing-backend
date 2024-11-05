package com.owing.api.trashcan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.api.trashcan.service.CreateTrashCanUserCase;
import com.owing.api.trashcan.service.DeleteTrashCanUserCase;
import com.owing.api.trashcan.service.ReadTrashCanUserCase;
import com.owing.api.trashcan.service.UpdateTrashCanUserCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/trashCans")
@RequiredArgsConstructor
public class TrashCanController {

	private final CreateTrashCanUserCase createTrashCanUserCase;
	private final ReadTrashCanUserCase readTrashCanUserCase;
	private final UpdateTrashCanUserCase updateTrashCanUserCase;
	private final DeleteTrashCanUserCase deleteTrashCanUserCase;

	@GetMapping("")
	public ResponseEntity<TrashCanFolderResponse> getTrashCan(@RequestParam("project") Long projectId) {
		return ResponseEntity.ok(readTrashCanUserCase.execute(projectId));
	}

}

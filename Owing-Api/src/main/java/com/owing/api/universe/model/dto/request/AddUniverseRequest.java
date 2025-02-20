package com.owing.api.universe.model.dto.request;

import com.owing.api.dnd.dto.request.AddFileRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddUniverseRequest (

	@NotNull(message = "소속되는 folderId는 필수적으로 들어가야 합니다.")
	Long folderId,

	@NotBlank(message = "세계관 이름은 필수적으로 들어가야 합니다.")
	String name,

	@NotBlank(message = "세계관 설명은 필수적으로 들어가야 합니다.")
	String description,

	String imageUrl
) implements AddFileRequest { }
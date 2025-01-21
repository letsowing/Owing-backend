package com.owing.api.universe.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateUniverseRequest(

	@NotBlank(message = "세계관 이름은 필수적으로 들어가야합니다.")
	String name,

	@NotBlank(message = "세계관 설명은 필수적으로 들어가야합니다.")
	String description,

	String imageUrl
) { }

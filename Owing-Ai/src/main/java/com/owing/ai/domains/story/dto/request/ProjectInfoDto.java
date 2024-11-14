package com.owing.ai.domains.story.dto.request;

import java.util.List;

public record ProjectInfoDto(
	String title,
	String description,
	String category,
	List<String> genres
) {
}

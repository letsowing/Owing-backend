package com.owing.api.story.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record CrashCheckLogResponse(
	Long id,
	List<CrashCheckItemResponse> crashCheckItemResponseList,
	LocalDateTime createdAt
) {
}

package com.owing.ai.domains.story.ai.v2.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import com.owing.ai.domains.story.ai.v2.model.entity.StoryInfoType;
import com.owing.ai.domains.story.dto.request.crashCheck.StoryInfoDto;

public interface DocumentConvertService {
	List<Document> convertToDocument(LocalDateTime last, List<? extends StoryInfoDto> data, StoryInfoType type, Long projectId);

	default Map<String, Object> getMetadataMap(Long projectId, LocalDateTime updatedAt, Long id, StoryInfoType type) {
		return Map.of(
			"projectId", projectId,
			"updatedAt", updatedAt.toString(),
			"id", id,
			"type", type.toString(),
			"deleted", false
		);
	}


}

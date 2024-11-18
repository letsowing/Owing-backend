package com.owing.ai.domains.story.ai.v2.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.ai.domains.story.ai.v2.model.entity.StoryInfoType;
import com.owing.ai.domains.story.dto.request.crashCheck.CrashCheckRequest;
import com.owing.ai.domains.story.dto.request.crashCheck.StoryInfoDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DocumentConverter {
	private final ObjectMapper objectMapper;

	public List<Document> filterNewContent(CrashCheckRequest request, LocalDateTime last, Long projectId){
		List<Document> result = new ArrayList<>();

		result.addAll(convertToDocument(last, request.prevStory(), StoryInfoType.STORY, projectId));
		result.addAll(convertToDocument(last, request.universe(), StoryInfoType.UNIVERSE, projectId));
		result.addAll(convertToDocument(last, request.cast().castList(), StoryInfoType.CAST, projectId));
		result.addAll(convertToDocument(last, request.cast().relationList(), StoryInfoType.RELATIONSHIP, projectId));
		Optional.ofNullable(convertToDocument(last, request.project(), StoryInfoType.PROJECT, projectId)).ifPresent(result::add);
		Optional.ofNullable(convertToDocument(last, request.thisEpisode(), StoryInfoType.STORY, projectId)).ifPresent(result::add);

		return result;
	}

	public Map<String, List<String>> getDeleteIds(List<Document> documents){
		Map<String, List<String>> result = new HashMap<>();

		for (Document doc : documents) {
			String type = (String)doc.getMetadata().get("type");
			String id = doc.getMetadata().get("id").toString();

			if (result.containsKey(type)) {
				result.get(type).add(id);
			} else {
				result.put(type, new ArrayList<>(){{add(id);}});
			}
		}
		return result;
	}

	private List<Document> convertToDocument(LocalDateTime last, List<? extends StoryInfoDto> data, StoryInfoType type, Long projectId){
        return data.stream().filter(r -> r.isNewContent(last)).map(r -> preprocess(r, type, projectId)).toList();
    }

	private Document convertToDocument(LocalDateTime last, StoryInfoDto dto, StoryInfoType type, Long projectId){
		return filterAndGenerateDocument(last, dto, type, projectId);
	}

	private Document filterAndGenerateDocument(LocalDateTime last, StoryInfoDto dto, StoryInfoType type,
		Long projectId) {
		if (dto.isNewContent(last)) {
			return preprocess(dto, type, projectId);
		}
		return null;
	}

	private Document preprocess(StoryInfoDto value, StoryInfoType type, Long projectId) {
		try {
			String jsonString = objectMapper.writeValueAsString(value); // fixme 걍 json 말고 내용만 드가게
			return new Document(jsonString, getMetadataMap(projectId, value.updatedAt(), value.id(), type));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private Map<String, Object> getMetadataMap(Long projectId, LocalDateTime updatedAt, Long id, StoryInfoType type) {
		return Map.of(
			"projectId", projectId,
			"updatedAt", updatedAt.toString(),
			"id", id,
			"type", type.toString(),
			"deleted", false
		);
	}
}

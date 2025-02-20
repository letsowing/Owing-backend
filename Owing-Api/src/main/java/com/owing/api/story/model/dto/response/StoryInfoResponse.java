package com.owing.api.story.model.dto.response;

import com.owing.api.dnd.dto.response.FileInfoResponse;
import com.owing.entity.domains.story.model.Story;

import lombok.Builder;

@Builder
public record StoryInfoResponse(
	Long storyId,
	String name,
	String description,
	int textCount,
	String content

) implements FileInfoResponse {
	public static StoryInfoResponse from(Story story){
		return StoryInfoResponse.builder()
				.storyId(story.getId())
				.name(story.getName())
				.description(story.getDescription())
				.textCount(story.getTextCount())
				.content(story.getContent())
				.build();
	}
}

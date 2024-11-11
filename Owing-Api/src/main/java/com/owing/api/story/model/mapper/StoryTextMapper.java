package com.owing.api.story.model.mapper;

import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryText;

@Mapper
public class StoryTextMapper {
	public StoryText toEntity(String content, Story story){
		return StoryText.builder()
			.content(content)
			.story(story)
            .build();
	}
}

package com.owing.api.story.model.mapper;

import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryContent;

@Mapper
public class StoryContentMapper {
	public StoryContent toEntity(String content, Story story){
		return StoryContent.builder()
			.content(content)
			.story(story)
            .build();
	}
}

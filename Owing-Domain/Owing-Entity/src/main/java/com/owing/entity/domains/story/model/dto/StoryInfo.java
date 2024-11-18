package com.owing.entity.domains.story.model.dto;

import lombok.Builder;

@Builder
public record StoryInfo(
        Long storyId,
        String name,
        String description,
        int textCount,
        String content
){}


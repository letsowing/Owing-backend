package com.owing.ai.domains.story.ai.v2.model.entity;

import lombok.Getter;

@Getter
public enum StoryInfoType {
	PROJECT("작품 관련 정보"),
	UNIVERSE("세계관 정보"),
    CAST("등장인물 관련 정보"),
	RELATIONSHIP("관계 정보"),
    STORY("이전 스토리 정보");

    private final String description;

    StoryInfoType(String description) {
        this.description = description;
    }

	@Override
	public String toString() {
		return name().toUpperCase();
	}
}

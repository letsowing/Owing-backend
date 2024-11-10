package com.owing.entity.domains.story.model;

import org.hibernate.annotations.SoftDelete;

import com.owing.entity.common.model.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDelete
public class StoryText extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(mappedBy = "storyText")
	private Story story;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String content;

	@Builder
	public StoryText(Story story, String content) {
		this.story = story;
		this.content = content;
	}

	public void update(StoryText newStoryText) {
        this.content = newStoryText.content;
    }
}

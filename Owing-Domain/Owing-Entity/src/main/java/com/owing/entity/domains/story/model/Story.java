package com.owing.entity.domains.story.model;

import org.hibernate.annotations.SoftDelete;

import com.owing.core.dnd.file.model.BaseFileEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDelete
public class Story extends BaseFileEntity<StoryFolder> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "int default 0", nullable = false)
	private int textCount;

	@Builder
	Story(String title, String description, Long position, int textCount, StoryFolder folder) {
		this.title = title;
		this.description = description;
		this.position = position;
		this.textCount = textCount;
		this.folder = folder;
	}

	public void updateTextCount(int textCount){
		if(textCount < 0){
			throw new IllegalArgumentException("Text count must be non-negative.");
		}
		this.textCount = textCount;
	}

}

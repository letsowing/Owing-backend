package com.owing.entity.domains.story.model;

import java.util.Optional;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.owing.entity.dnd.file.model.DndFileEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE story SET deleted = true where id = ?")
public class Story extends DndFileEntity<StoryFolder> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "int default 0", nullable = false)
	private int textCount;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private StoryContent storyContent;

	@Builder
	Story(String name, String description, Long position, int textCount, StoryFolder folder, StoryContent storyContent) {
		this.name = name;
		this.description = description;
		this.position = position;
		this.textCount = textCount;
		this.folder = folder;
		this.storyContent = storyContent;
	}

	public void updateTextCount(int textCount){
		if(textCount < 0){
			throw new IllegalArgumentException("Text count must be non-negative.");
		}
		this.textCount = textCount;
	}


	public void createOrUpdateStoryText(StoryContent newStoryContent) {
		if (this.storyContent == null) {
			this.storyContent = newStoryContent;
		} else {
			this.storyContent.update(newStoryContent);
		}
	}

	public String getContent() {
		return Optional.ofNullable(storyContent)
				.map(StoryContent::getContent)
				.orElse("");
	}
}

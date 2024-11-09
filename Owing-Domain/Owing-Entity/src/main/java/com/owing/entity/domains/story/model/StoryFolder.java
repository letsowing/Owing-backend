package com.owing.entity.domains.story.model;

import org.hibernate.annotations.SoftDelete;

import com.owing.core.dnd.folder.model.BaseFolderEntity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDelete
public class StoryFolder extends BaseFolderEntity {

	@Builder
	StoryFolder(Long projectId, String name, String description, Long position) {
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		this.position = position;
	}

}

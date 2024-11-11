package com.owing.entity.domains.story.model;

import com.owing.core.dnd.file.model.BaseFileEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SoftDelete;

import com.owing.core.dnd.folder.model.BaseFolderEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDelete
public class StoryFolder extends BaseFolderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	protected List<Story> files = new ArrayList<>();

	@Builder
	StoryFolder(Long projectId, String name, String description, Long position) {
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		this.position = position;
	}

}

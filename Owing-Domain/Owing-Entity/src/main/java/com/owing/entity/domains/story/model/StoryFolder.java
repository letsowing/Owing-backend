package com.owing.entity.domains.story.model;

import org.hibernate.annotations.SoftDelete;

import com.owing.core.dnd.folder.model.BaseFolderEntity;

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
public class StoryFolder extends BaseFolderEntity<Story> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @OrderBy("position ASC")
	// @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	// protected List<Story> files = new ArrayList<>();

	@Builder
	StoryFolder(Long projectId, String name, String description, Long position) {
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		this.position = position;
	}

}

package com.owing.entity.folders.story.model;

import com.owing.entity.common.constant.OwingPersistenceConst;
import com.owing.entity.common.model.BaseTimeEntity;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.model.Story;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDelete
public class StoryFolder extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	private String name;

	@Column(length = OwingPersistenceConst.DESC_LEN)
	private String description;

	@Column(nullable = false)
	private Long position;

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	@OneToMany(mappedBy = "storyFolder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Story> storyList = new ArrayList<>();

	@Builder
	StoryFolder(Project project, String name, String description, Long position) {
		this.project = project;
		this.name = name;
		this.description = description;
		this.position = position;
	}

}

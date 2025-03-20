package com.owing.entity.domains.project.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ProjectDomainService {

	private final ProjectAdapter projectAdapter;
	private final StoryFolderAdapter storyFolderAdapter;
	private final UniverseFolderAdapter universeFolderAdapter;

	@Transactional("jpaTransactionManager")
	public Project createProjectEntity(Project project) {
		project = projectAdapter.save(project);
		StoryFolder initialStoryFolder = StoryFolder.init(project.getId());
		UniverseFolder initialUniverseFolder = UniverseFolder.init(project.getId());

		storyFolderAdapter.save(initialStoryFolder);
		universeFolderAdapter.save(initialUniverseFolder);

		return project;
	}

}

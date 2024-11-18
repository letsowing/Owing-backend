package com.owing.api.story.model.dto.request.ai.crashCheck;

import java.time.LocalDateTime;
import java.util.Set;

import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Genre;
import com.owing.entity.domains.project.model.Project;

public record ProjectInfoDto(
	Long id,
	String title,
	String description,
	Category category,
	Set<Genre> genres,
	LocalDateTime updatedAt
) {
	public static ProjectInfoDto from(Project project) {
		return new ProjectInfoDto(
			project.getId(),
			project.getProjectInfo().getTitle(),
			project.getProjectInfo().getDescription(),
			project.getProjectInfo().getCategory(),
			project.getProjectInfo().getGenres(),
			project.getUpdatedAt()
		);
	}
}

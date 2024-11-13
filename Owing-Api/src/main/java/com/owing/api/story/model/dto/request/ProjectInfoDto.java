package com.owing.api.story.model.dto.request;

import java.util.Set;

import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Genre;
import com.owing.entity.domains.project.model.ProjectInfo;

public record ProjectInfoDto(
	String title,
	String description,
	Category category,
	Set<Genre> genres
) {
	public static ProjectInfoDto from(ProjectInfo projectInfo) {
		return new ProjectInfoDto(
			projectInfo.getTitle(),
			projectInfo.getDescription(),
			projectInfo.getCategory(),
			projectInfo.getGenres()
		);
	}
}

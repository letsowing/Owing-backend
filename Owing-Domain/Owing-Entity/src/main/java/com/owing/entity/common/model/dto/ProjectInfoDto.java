package com.owing.entity.common.model.dto;

import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Genre;
import lombok.Builder;

import java.util.Set;

@Builder
public record ProjectInfoDto(
        String title,
        String description,
        Category category,
        Set<Genre> genres,
        String coverUrl
) {
}

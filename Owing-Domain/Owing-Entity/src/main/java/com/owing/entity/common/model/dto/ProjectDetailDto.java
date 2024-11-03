package com.owing.entity.common.model.dto;

import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Genre;

import java.util.Set;

public record ProjectDetailDto(
        Long id,
        String description,
        Category category,
        Set<Genre> genres
) {
}

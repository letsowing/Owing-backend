package com.owing.entity.common.vo;

import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Genre;

import java.util.Set;

public record ProjectDetailVo(
        Long id,
        String description,
        Category category,
        Set<Genre> genres
) {
}

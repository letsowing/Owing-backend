package com.owing.api.project.model.dto.request;

import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Genre;

import java.util.Set;

public interface ProjectInfoRequest {
    String title();
    String description();
    Category category();
    Set<Genre> genres();
    String coverUrl();
}

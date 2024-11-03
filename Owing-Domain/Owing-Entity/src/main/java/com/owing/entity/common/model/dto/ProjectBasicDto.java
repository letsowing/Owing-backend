package com.owing.entity.common.model.dto;

import com.owing.entity.domains.project.model.Project;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProjectBasicDto(
        Long id,
        String title,
        String coverUrl,
        LocalDateTime accessedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static ProjectBasicDto from(Project project) {
        return ProjectBasicDto.builder()
                .id(project.getId())
                .title(project.getTitle())
                .coverUrl(project.getCoverUrl())
                .accessedAt(project.getAccessedAt())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}

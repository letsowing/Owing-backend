package com.owing.entity.common.vo;

import com.owing.entity.domains.project.model.Project;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProjectBasicVo(
        Long id,
        String title,
        String coverUrl,
        LocalDateTime accessedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static ProjectBasicVo from(Project project) {
        return ProjectBasicVo.builder()
                .id(project.getId())
                .title(project.getTitle())
                .coverUrl(project.getCoverUrl())
                .accessedAt(project.getAccessedAt())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}

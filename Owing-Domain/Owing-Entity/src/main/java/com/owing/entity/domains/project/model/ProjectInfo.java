package com.owing.entity.domains.project.model;

import com.owing.entity.common.constant.OwingPersistenceConst;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.util.ObjectUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Set;

@Embeddable
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectInfo {

    @Column(length = OwingPersistenceConst.TITLE_LEN, nullable = false)
    private String title;

    @Column(length = OwingPersistenceConst.DESC_LEN, nullable = false)
    private String description;

    @Column(length = OwingPersistenceConst.CATEGORY_LEN, nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "genres", columnDefinition = "varchar[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres;

    @Column(length = OwingPersistenceConst.URL_LEN)
    private String coverUrl;


    public void updateTitle(String title) {
        if (ObjectUtils.isEmpty(title)) {
            return;
        }
        this.title = title;
    }

    public void updateDescription(String description) {
        if (ObjectUtils.isEmpty(description)) {
            return;
        }
        this.description = description;
    }

    public void updateCategory(Category category) {
        if (ObjectUtils.isEmpty(category)) {
            return;
        }
        this.category = category;
    }

    public void updateGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public void updateCoverUrl(String coverUrl) {
        if (ObjectUtils.isEmpty(coverUrl)) {
            return;
        }
        this.coverUrl = coverUrl;
    }

    public void updateProjectInfo(ProjectInfo projectInfo) {
        this.updateTitle(projectInfo.getTitle());
        this.updateDescription(projectInfo.getDescription());
        this.updateCategory(projectInfo.getCategory());
        this.updateGenres(projectInfo.getGenres());
        this.updateCoverUrl(projectInfo.getCoverUrl());
    }
}

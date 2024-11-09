package com.owing.entity.domains.project.model;

import java.util.Set;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.owing.entity.common.constant.OwingPersistenceConst;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        this.title = title;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

    public void updateGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public void updateCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

}

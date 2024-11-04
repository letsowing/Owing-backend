package com.owing.entity.domains.project.model;

import com.owing.entity.common.constant.OwingPersistenceConst;
import com.owing.entity.common.model.BaseTimeEntity;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.folders.story.model.StoryFolder;
import com.owing.entity.folders.universe.model.UniverseFolder;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

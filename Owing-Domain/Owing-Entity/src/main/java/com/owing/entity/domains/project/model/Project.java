package com.owing.entity.domains.project.model;

import com.owing.entity.common.constant.OwingPersistenceConst;
import com.owing.entity.common.model.BaseTimeEntity;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.folders.story.model.StoryFolder;
import com.owing.entity.folders.universe.model.UniverseFolder;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SoftDelete
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(nullable = false)
    private LocalDateTime accessedAt;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StoryFolder> storyFolderList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UniverseFolder> universeFolderList = new ArrayList<>();

    @PrePersist
    protected void prePersist() {
        if (accessedAt == null) {
            LocalDateTime now = LocalDateTime.now();
            accessedAt = now;
        }
    }

}

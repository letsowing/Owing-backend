package com.owing.entity.domains.project.model;

import com.owing.entity.common.model.BaseTimeEntity;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.folders.story.model.StoryFolder;
import com.owing.entity.folders.universe.model.UniverseFolder;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Embedded
    private ProjectInfo projectInfo;

    @Column(nullable = false)
    private LocalDateTime accessedAt;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StoryFolder> storyFolderList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UniverseFolder> universeFolderList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TrashCan> trashCanList = new ArrayList<>();

    @PrePersist
    protected void prePersist() {
        if (accessedAt == null) {
            LocalDateTime now = LocalDateTime.now();
            accessedAt = now;
        }
    }
}

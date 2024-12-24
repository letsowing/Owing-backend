package com.owing.entity.domains.project.model;

import java.time.LocalDateTime;

import com.owing.core.BaseEntity;
import com.owing.entity.domains.member.model.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Project extends BaseEntity {

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

    // @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // private List<StoryFolder> storyFolderList = new ArrayList<>();

    // @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // private List<UniverseFolder> universeFolderList = new ArrayList<>();

    @PrePersist
    protected void prePersist() {
        if (accessedAt == null) {
            LocalDateTime now = LocalDateTime.now();
            accessedAt = now;
        }
    }

    public void updateAccessedAt(LocalDateTime localDateTime) {
        if (localDateTime.isBefore(this.accessedAt)) {
            return;
        }
        this.accessedAt = localDateTime;
    }
}

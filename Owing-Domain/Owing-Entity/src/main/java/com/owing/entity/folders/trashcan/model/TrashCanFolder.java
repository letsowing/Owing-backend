package com.owing.entity.folders.trashcan.model;

import java.util.ArrayList;
import java.util.List;

import com.owing.core.constant.OwingPersistenceConst;
import com.owing.entity.common.model.BaseTimeEntity;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.trashcan.model.TrashCan;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TrashCanFolder extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private FolderType tableName;

    @Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
    private String name;

    @Column(length = OwingPersistenceConst.DESC_LEN)
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "trashCanFolder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TrashCan> trashCanList = new ArrayList<>();
}

package com.owing.entity.folders.universe.model;

import java.util.ArrayList;
import java.util.List;

import com.owing.core.constant.OwingPersistenceConst;
import com.owing.entity.common.model.BaseTimeEntity;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.universe.model.Universe;
import jakarta.persistence.*;
import org.hibernate.annotations.SoftDelete;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@SoftDelete
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UniverseFolder extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
    private String name;

    @Column(length = OwingPersistenceConst.DESC_LEN)
    private String description;

    @Column(nullable = false)
    private Long position;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "universeFolder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Universe> universeList = new ArrayList<>();

}

package com.owing.entity.domains.universe.model;

import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.file.model.BaseFileEntity;
import com.owing.core.dnd.folder.model.BaseFolder;
import jakarta.persistence.*;
import org.hibernate.annotations.SoftDelete;

import com.owing.core.dnd.folder.model.BaseFolderEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SoftDelete
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UniverseFolder extends BaseFolderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
     private List<Universe> files = new ArrayList<>();

    @Builder
    public UniverseFolder(Long id, String name, String description, Long projectId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectId = projectId;
    }

}

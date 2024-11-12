package com.owing.entity.domains.universe.model;

import org.hibernate.annotations.SoftDelete;

import com.owing.core.dnd.folder.model.BaseFolderEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@SoftDelete
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UniverseFolder extends BaseFolderEntity<Universe> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder
    public UniverseFolder(Long id, String name, String description, Long projectId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectId = projectId;
    }

}

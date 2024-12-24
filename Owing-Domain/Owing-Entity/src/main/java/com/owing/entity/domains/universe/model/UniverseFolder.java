package com.owing.entity.domains.universe.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.owing.entity.dnd.folder.model.DndFolderEntity;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE universe_folder SET deleted = true where id = ?")
public class UniverseFolder extends DndFolderEntity<Universe> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder(builderClassName = "BasicBuilder", builderMethodName = "basicBuilder")
    public UniverseFolder(Long id, String name, String description, Long projectId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectId = projectId;
    }

    @Builder(builderClassName = "PositionBuilder", builderMethodName = "positionBuilder")
    public UniverseFolder(Long id, String name, String description, Long projectId, Long position) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectId = projectId;
        this.position = position;
    }
}

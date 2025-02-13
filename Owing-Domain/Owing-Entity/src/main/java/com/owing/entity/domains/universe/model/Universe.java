package com.owing.entity.domains.universe.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.owing.core.constant.OwingPersistenceConst;
import com.owing.entity.dnd.file.model.DndFileEntity;

import jakarta.persistence.Column;
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
@SQLDelete(sql = "UPDATE universe SET deleted = true where id = ?")
public class Universe extends DndFileEntity<UniverseFolder> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(length = OwingPersistenceConst.URL_LEN)
	private String imageUrl;

	public Universe update(Universe newUniverse) {
		this.name = newUniverse.name;
		this.description = newUniverse.description;
		this.imageUrl = newUniverse.imageUrl;
		return this;
	}

	@Builder
	public Universe(Long id, String description, String imageUrl, String name, Long position, UniverseFolder folder) {
		this.id = id;
		this.description = description;
		this.imageUrl = imageUrl;
		this.name = name;
		this.position = position;
		this.folder = folder;
	}

}

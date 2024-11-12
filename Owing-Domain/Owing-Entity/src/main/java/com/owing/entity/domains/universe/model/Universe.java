package com.owing.entity.domains.universe.model;

import org.hibernate.annotations.SoftDelete;

import com.owing.core.constant.OwingPersistenceConst;
import com.owing.core.dnd.file.model.BaseFileEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@SoftDelete
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Universe extends BaseFileEntity<UniverseFolder> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(length = OwingPersistenceConst.URL_LEN)
	private String imageUrl;

	public Universe updateUniverse(Universe newUniverse) {
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

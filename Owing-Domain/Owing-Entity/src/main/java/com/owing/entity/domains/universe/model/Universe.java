package com.owing.entity.domains.universe.model;

import com.owing.entity.common.constant.OwingPersistenceConst;
import com.owing.entity.common.model.BaseTimeEntity;
import com.owing.entity.folders.universe.model.UniverseFolder;
import org.hibernate.annotations.SoftDelete;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@SoftDelete
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Universe extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	private String name;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(length = OwingPersistenceConst.URL_LEN)
	private String imageUrl;

	@ManyToOne
	@JoinColumn(name = "universe_folder_id", nullable = false)
	private UniverseFolder universeFolder;
}

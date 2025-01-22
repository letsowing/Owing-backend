package com.owing.entity.domains.trashcan.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.owing.core.constant.OwingPersistenceConst;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "trash_can")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class TrashCan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = OwingPersistenceConst.ITEM_ID, nullable = false)
	private Long itemId;

	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	private String name;

	@Column(length = OwingPersistenceConst.DESC_LEN)
	private String description;

	@Column(length = OwingPersistenceConst.URL_LEN)
	private String imageUrl;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@ManyToOne
	@JoinColumn(name = "trash_can_folder_Id", nullable = false)
	private TrashCanFolder trashCanFolder;

	@Builder
	public TrashCan(Long id, Long itemId, String name, String description, LocalDateTime createdAt, TrashCanFolder trashCanFolder, String imageUrl) {
		this.id = id;
		this.itemId = itemId;
		this.name = name;
		this.description = description;
		this.createdAt = createdAt;
		this.trashCanFolder = trashCanFolder;
		this.imageUrl = imageUrl;
	}

	public FolderType getTableName() {
		return trashCanFolder.getTableName();
	}
}

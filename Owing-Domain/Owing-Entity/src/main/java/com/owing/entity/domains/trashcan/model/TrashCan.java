package com.owing.entity.domains.trashcan.model;

import com.owing.core.constant.OwingPersistenceConst;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

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

	@Column(length = OwingPersistenceConst.FILE_NAME_LEN)
	private String description;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@ManyToOne
	@JoinColumn(name = "trash_can_folder_Id", nullable = false)
	private TrashCanFolder trashCanFolder;

	@Builder
	public TrashCan(Long id, Long itemId, String name, String description, LocalDateTime createdAt, TrashCanFolder trashCanFolder) {
		this.id = id;
		this.itemId = itemId;
		this.name = name;
		this.description = description;
		this.createdAt = createdAt;
		this.trashCanFolder = trashCanFolder;
	}
}

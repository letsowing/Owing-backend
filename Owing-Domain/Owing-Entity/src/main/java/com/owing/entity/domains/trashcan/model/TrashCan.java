package com.owing.entity.domains.trashcan.model;

import com.owing.core.constant.OwingPersistenceConst;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrashCan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = OwingPersistenceConst.TABLE_NAME_LEN, nullable = false)
	private String tableName;

	@Column(length = OwingPersistenceConst.ITEM_ID, nullable = false)
	private String itemId;

	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	private String itemTitle;

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
	private LocalDateTime createdAt;

}

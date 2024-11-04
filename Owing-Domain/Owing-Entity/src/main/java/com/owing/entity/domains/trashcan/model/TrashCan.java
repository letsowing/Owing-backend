package com.owing.entity.domains.trashcan.model;

import com.owing.entity.common.constant.OwingPersistenceConst;
import com.owing.entity.common.model.BaseTimeEntity;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.project.model.Project;
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

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;
}

package com.owing.entity.dnd.folder.model;

import com.owing.entity.common.constant.OwingPersistenceConst;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BaseFolderNode implements BaseFolder {
	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	protected String name;

	@Column(name = "project_id")
	protected Long projectId;
	//
	// @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
	// private List<BaseFileEntity> files = new ArrayList<>();

	@Override
	public Long getParentId() {
		return this.projectId;
	}

	@Override
	public boolean validatePosition(long newPosition) {
		return newPosition >= 0;
	}
}

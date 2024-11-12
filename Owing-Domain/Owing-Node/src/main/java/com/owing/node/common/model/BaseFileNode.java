package com.owing.node.common.model;

import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.util.StringUtils;

import com.owing.core.constant.OwingPersistenceConst;
import com.owing.core.dnd.base.error.DndErrorCode;
import com.owing.core.dnd.base.error.exception.DndException;
import com.owing.core.dnd.base.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.file.model.BaseFile;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public abstract class BaseFileNode<T extends BaseFolderNode> extends BaseTimeNeo4j implements BaseFile<T>{


	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	protected String name;

	@Column(length = OwingPersistenceConst.DESC_LEN)
	protected String description;

	@Column(nullable = false)
	protected Long position;

	@Relationship(type = "INCLUDE", direction = Relationship.Direction.INCOMING)
	protected T folder;

	public void update(BaseFileNode<T> newFile){
		this.name = newFile.getName();
		this.description = newFile.getDescription();
	}

	public void updateFolder(T folder){
		this.folder = folder;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Long getParentId(){
		return folder.getId();
	}

	@Override
	public boolean validatePosition(long newPosition) {
		return newPosition >= 0;
	}

	public abstract void connectFolder(T folder);

	@Override
	public void updateName(String newTitle) {
		if (!StringUtils.hasText(newTitle)) {
			throw DndException.of(DndErrorCode.INVALID_TITLE);
		}
		this.name = newTitle;
	}

	@Override
	public void updatePosition(long newPosition) {
		if (!validatePosition(newPosition)) {
			throw DndInvalidPositionException.of(DndErrorCode.INVALID_POSITION);
		}
		this.position = newPosition;
	}
}

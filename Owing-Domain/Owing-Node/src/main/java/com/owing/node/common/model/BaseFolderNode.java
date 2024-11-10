package com.owing.node.common.model;


import com.owing.core.constant.OwingPersistenceConst;
import com.owing.core.dnd.base.error.DndErrorCode;
import com.owing.core.dnd.base.error.exception.DndException;
import com.owing.core.dnd.base.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.folder.model.BaseFolder;
import jakarta.persistence.Column;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public abstract class BaseFolderNode extends BaseTimeNeo4j implements BaseFolder {
	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	protected String name;

	protected Long position;
	protected Long projectId;

	@Override
	public Long getParentId() {
		return this.projectId;
	}

	@Override
	public boolean validatePosition(long newPosition) {
		return newPosition >= 0;
	}

	@Override
	public void updateTitle(String newTitle) {
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

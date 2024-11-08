package com.owing.entity.dnd.base.model;

import com.owing.entity.common.constant.OwingPersistenceConst;
import com.owing.entity.dnd.base.error.DndErrorCode;
import com.owing.entity.dnd.base.error.exception.DndInvalidPositionException;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class DndEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	protected Long position;
	@Column(length = OwingPersistenceConst.DESC_LEN)
	protected String description;

	public abstract void update(DndEntity newEntity);

	public abstract Long getParentId();

	public abstract boolean validatePosition(long newPosition);

	public void updatePosition(long newPosition) {
		if(!validatePosition(newPosition)){
			throw DndInvalidPositionException.of(DndErrorCode.INVALID_POSITION);
		}
		this.position = newPosition;
	}
}

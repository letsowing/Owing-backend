package com.owing.node.common.model;

//import jakarta.persistence.EntityListeners;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
public abstract class BaseTimeNeo4j {

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	private Boolean deleted = Boolean.FALSE;

	public void delete() {
		this.deleted = Boolean.TRUE;
	}

	public void restore() {
		this.deleted = Boolean.FALSE;
	}

	public boolean isDeleted() {
		return this.deleted;
	}
}

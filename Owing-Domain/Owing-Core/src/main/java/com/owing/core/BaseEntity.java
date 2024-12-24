package com.owing.core;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
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

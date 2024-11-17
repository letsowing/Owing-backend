package com.owing.ai.domains.story.ai.v2.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SoftDelete;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SoftDelete
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class VectorStoreMetadata {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long projectId;
	private LocalDateTime updatedAt;

	public void updateUpdatedAt() {
		this.updatedAt = LocalDateTime.now();
	}
}

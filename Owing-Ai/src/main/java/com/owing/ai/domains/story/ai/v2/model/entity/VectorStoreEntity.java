package com.owing.ai.domains.story.ai.v2.model.entity;

import java.util.Arrays;
import java.util.UUID;

import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "vector_store")
@Getter
public class VectorStoreEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@JdbcTypeCode(SqlTypes.VECTOR)
	@Array(length = 1536)
	private float[] embedding;

	@Column(columnDefinition = "TEXT")
	private String content;

	@JdbcTypeCode(SqlTypes.JSON)
	private String metadata;

	@Override
	public String toString() {
		return "VectorStoreEntity{" +
			   "id=" + id +
			   ", embedding=" + Arrays.toString(embedding) +
			   ", content='" + content + '\'' +
			   ", metadata='" + metadata + '\'' +
			   '}';
	}
}

/*
	id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
	content text,
	metadata json,
	embedding vector(1536) //
 */

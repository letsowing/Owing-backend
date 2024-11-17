package com.owing.ai.domains.story.ai.v2.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owing.ai.domains.story.ai.v2.model.entity.VectorStoreMetadata;

public interface VectorStoreMetadataRepository extends JpaRepository<VectorStoreMetadata, Long> {
	Optional<VectorStoreMetadata> findByProjectId(Long projectId);
}

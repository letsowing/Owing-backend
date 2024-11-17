package com.owing.ai.domains.story.ai.v2.model.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.owing.ai.domains.story.ai.v2.model.entity.VectorStoreEntity;

public interface VectorStoreRepository extends JpaRepository<VectorStoreEntity, UUID> {
	@Transactional
	@Modifying
	@Query(value = """
		UPDATE vector_store
		SET metadata = metadata::jsonb || '{"deleted" : true}'
		WHERE
			metadata ->>'projectId' = :projectId
			AND metadata ->>'deleted' = 'false'
		  	AND metadata ->>'type' = :type
			AND metadata->>'id' IN :ids
		""", nativeQuery = true)
	void deleteByTypeAndIdIn(String projectId, String type, List<String> ids);

	@Query(value = """
		WITH source_vectors AS (
		    SELECT id, embedding
		    FROM vector_store
		    WHERE metadata->>'projectId' = :projectId
		       AND metadata->>'type' = 'STORY'
		       AND metadata->>'id' = :storyId
		       AND metadata->>'deleted' = 'false'
		),
		     similarity_scores AS (
		         SELECT
		             tv.id,
		             tv.embedding,
		             tv.content,
		             tv.metadata,
		             1 - (sv.embedding <=> tv.embedding) as similarity
		         FROM source_vectors sv
		                  CROSS JOIN vector_store tv
		          WHERE tv.metadata->>'projectId' = :projectId
		            AND NOT(tv.metadata->>'type' = 'STORY' AND tv.metadata->>'id' = :storyId)
		            AND tv.metadata->>'deleted' = 'false'
		     ),
		     ranked_results AS (
		         SELECT
		             *,
		             ROW_NUMBER() OVER (
		                 PARTITION BY id
		                 ORDER BY similarity DESC
		                 ) as rank
		         FROM similarity_scores
		         WHERE similarity > :similarity
		     )
		SELECT *
		FROM ranked_results
		WHERE rank = 1
		ORDER BY similarity DESC
		LIMIT :topK
		""", nativeQuery = true)
	List<VectorStoreEntity> similaritySearch(String projectId, String storyId, double similarity, int topK);
}

package com.owing.node.domains.cast.repository;

import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.common.repository.BaseFileNodeRepository;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.projection.CastAiProjection;
import com.owing.node.domains.cast.model.projection.CastRelationshipAiProjection;
import com.owing.node.domains.cast.model.projection.CastGraphNodeProjection;
import com.owing.node.domains.cast.model.projection.CastGraphRelationshipProjection;
import com.owing.node.folder.cast.model.CastFolderNode;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CastNodeRepository extends BaseFileNodeRepository<CastNode, CastFolderNode> {

    @Query("""
            MATCH
                (c:Cast{deleted:false})<-[r:INCLUDE]-(cf:CastFolder{deleted:false})
            WHERE
                id(c)=$castId
            RETURN
                c, r, cf
            """)
    Optional<CastNode> findOneById(Long castId);

	@Query("""
		MATCH
		  (c:Cast{deleted:true})
		WHERE
		  id(c)=$itemId
		SET
		  c.deleted=false
		""")
	void restoreById(@Param("itemId") Long itemId);

	@Query("""
            MATCH
              (cf:CastFolder{deleted:false})
            WHERE
              id(cf)=$castFolderId
            MATCH
              (c:Cast{deleted:false})
            WHERE
              id(c)=$castId
            MERGE
              (cf)-[r:INCLUDE]->(c)
            RETURN
              c, r, cf
            """)
	CastNode connectFolder(Long castId, Long castFolderId);

	// =====Cast to Cast Connection=====
    @Query("""
            MATCH
                (n1:Cast{deleted:false})-[r:CONNECTION]->(n2:Cast{deleted:false})
            WHERE
                id(n1)=$sourceId and id(n2)=$targetId
            RETURN
                distinct id(r) as relationshipId,
                r.label as label,
                r.sourceId as sourceId,
                r.sourceHandle as sourceHandle,
                r.targetId as targetId,
                r.targetHandle as targetHandle
            """)
    Optional<CastRelationshipProjection> findConnection(Long sourceId, Long targetId);

    @Query("""
            MATCH
              (n1:Cast{deleted:false})-[r:BI_CONNECTION]-(n2:Cast{deleted:false})
            WHERE
              id(n1)=$sourceId and id(n2)=$targetId
            RETURN
              distinct id(r) as relationshipId,
              r.label as label,
              r.sourceId as sourceId,
              r.sourceHandle as sourceHandle,
              r.targetId as targetId,
              r.targetHandle as targetHandle
            """)
    Optional<CastRelationshipProjection> findBiconnection(Long sourceId, Long targetId);

	@Query("""
			MATCH
			  (c1:Cast{deleted:false})-[r:CONNECTION|BI_CONNECTION]->(c2:Cast{deleted:false})
			WHERE
			  id(r) = $relationshipId
			RETURN DISTINCT
			  id(r) as relationshipId,
			  r.label as label,
			  r.sourceId as sourceId,
			  r.sourceHandle as sourceHandle,
			  r.targetId as targetId,
			  r.targetHandle as targetHandle
			""")
	Optional<CastRelationshipProjection> findCastRelationshipById(Long relationshipId);

	@Query("""
			MATCH
			  (c1:Cast{deleted:false})-[r:CONNECTION|BI_CONNECTION]->(c2:Cast{deleted:false})
			WHERE
			  id(r) = $relationshipId
			SET
			  r.label = $label
			""")
	void updateCastRelationshipLabel(Long relationshipId, String label);

	@Query("""
			MATCH
			  (c1:Cast{deleted:false})-[r:CONNECTION|BI_CONNECTION]->(c2:Cast{deleted:false})
			WHERE
			  id(r) = $relationshipId
			SET
			  r += {sourceHandle: $sourceHandle, targetHandle: $targetHandle}
			""")
	void updateCastRelationshipHandle(Long relationshipId, String sourceHandle, String targetHandle);

    @Query("""
			MATCH
			  (c1:Cast{deleted:false})-[r:CONNECTION|BI_CONNECTION]-(c2:Cast{deleted:false})
			WHERE
			  id(r) = $relationshipId
			DELETE
			  r
			""")
    void deleteCastRelationshipById(Long relationshipId);

	@Query("""
			MATCH
			  (c1:Cast{deleted:false}), (c2:Cast{deleted:false})
			WHERE
			  id(c1)=$sourceId AND id(c2)=$targetId
			MERGE
			  (c1)-[r:`:#{literal(#type)}`]->(c2)
			ON CREATE SET
			  r.label = $label,
			  r.sourceId=id(c1), r.sourceHandle = $sourceHandle,
			  r.targetId=id(c2), r.targetHandle = $targetHandle
			""")
	void createCastRelationship(Long sourceId, Long targetId, String type, String label, String sourceHandle, String targetHandle);

	@Query("""
			OPTIONAL MATCH
			  (s1:Cast{deleted: false})-[r1:CONNECTION]->(t1:Cast{deleted: false})
			WHERE
			  id(s1)=$sourceId AND id(t1)=$targetId
			OPTIONAL MATCH
			  (s2:Cast{deleted: false})-[r2:BI_CONNECTION]-(t2:Cast{deleted: false})
			WHERE
			  id(s2)=$sourceId AND id(t2)=$targetId
			RETURN
			  count(r1)+count(r2) > 0
			""")
	Boolean existsCastRelationshipForConnection(Long sourceId, Long targetId);

	@Query("""
			MATCH
			  (s1:Cast{deleted: false})-[r1:CONNECTION|BI_CONNECTION]-(t1:Cast{deleted: false})
			WHERE
			  id(s1)=$sourceId AND id(t1)=$targetId
			RETURN
			  count(r1) > 0
			""")
	Boolean existsCastRelationshipForBiconnection(Long sourceId, Long targetId);

	// =====Cast Graph=====
    @Query("""
			MATCH
			  (p:Project{id: $projectId, deleted:false})-[r1:INCLUDE]->(cf:CastFolder{deleted:false})
			MATCH
			  (cf)-[r2:INCLUDE]-(c1:Cast{deleted:false})
			MATCH
			  (c1)-[r3]-(c2:Cast{deleted:false})
			RETURN DISTINCT
			  id(r3) as relationshipId, type(r3) as type, r3.label as label,
			  r3.sourceId as sourceId, r3.sourceHandle as sourceHandle,
			  r3.targetId as targetId, r3.targetHandle as targetHandle
			""")
    List<CastGraphRelationshipProjection> findGraphCastRelationshipByProjectId(Long projectId);

    @Query("""
			MATCH
			  (p:Project{id: $projectId, deleted:false})-[r1:INCLUDE]->(cf:CastFolder{deleted:false})
			MATCH
			  (cf)-[r2:INCLUDE]->(c:Cast{deleted:false})
			RETURN DISTINCT
			  id(c) as castId,
			  c.name as name,
			  c.role as role,
			  c.imageUrl as imageUrl,
			  c.`coordinate.x` as `coordinate.x`, c.`coordinate.y` as `coordinate.y`
			""")
    List<CastGraphNodeProjection> findGraphCastByProjectId(Long projectId);

	// =====AI Prompt=====
	@Query("""
			MATCH
			  (p:Project{id: $projectId, deleted: false})-[r1:INCLUDE]->(cf:CastFolder{deleted: false})
			MATCH
			  (cf)-[r2:INCLUDE]->(c1:Cast{deleted: false})
			MATCH
			  (c1)-[r3:CONNECTION|BI_CONNECTION]-(c2:Cast{deleted: false})
			RETURN DISTINCT
			  type(r3) as type,
			  r3.label as label,
			  r3.sourceId as sourceId,
			  r3.targetId as targetId
			""")
	List<CastRelationshipAiProjection> findAllCastRelationshipForAiPrompt(Long projectId);

	@Query("""
			PROFILE
			MATCH
			  (p:Project{id: $projectId, deleted: false})-[r1:INCLUDE]->(cf:CastFolder{deleted: false})
			MATCH
			  (cf:CastFolder{deleted: false})-[r:INCLUDE]->(c:Cast)
			RETURN
			  id(c) as castId,
			  c.name as name,
			  c.role as role,
			  c.gender as gender,
			  c.age as age,
			  c.description as description
			""")
	List<CastAiProjection> findAllCastForAiPrompt(Long projectId);


	// =====super() Cast Repository=====
    @Override
    @Query("""
        MATCH
          (cf:CastFolder{deleted:false})-[r:INCLUDE]->(c:Cast{deleted:false})
        WHERE
          id(cf)=$castFolderId
        ORDER BY
          c.position
        RETURN
          cf, r, c
        """)
    List<CastNode> findByParentId(Long castFolderId);

    @Override
    @Query("""
			MATCH
			  (cf:CastFolder{deleted:false})-[r:INCLUDE]->(c:Cast{deleted:false})
			WHERE
			  id(cf)=$folderId
			  AND
			    c.position > $position
			SET
			  c.position = c.position - 1
			""")
    void decrementPositionAfter(Long position, Long folderId);

    @Override
    @Query("""
			MATCH
			  (cf:CastFolder{deleted:false})-[r:INCLUDE]->(c:Cast{deleted:false})
			WHERE
			  id(cf)=$folderId
			  AND
			    c.position >= $start
			  AND
			    c.position <= $end
			SET
			  c.position = c.position - 1
			""")
    void decrementPositionBetween(Long start, Long end, Long folderId);

    @Override
    @Query("""
			MATCH
			  (cf:CastFolder{deleted:false})-[r:INCLUDE]->(c:Cast{deleted:false})
			WHERE
			  id(cf)=$folderId
			  AND
			    c.position >= $start
			  AND
			    c.position <= $end
			SET
			  c.position = c.position + 1
			""")
    void incrementPositionBetween(Long start, Long end, Long folderId);

    @Override
    @Query("""
			MATCH
				(cf:CastFolder{deleted:false})-[r:INCLUDE]->(c:Cast{deleted:false})
			WHERE
				id(cf) = $parentId
			RETURN
				COALESCE(MAX(c.position), -1)
			""")
    Long getMaxPositionByParentId(Long parentId);
}

package com.owing.node.domains.cast.repository;

import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.common.repository.BaseFileNodeRepository;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.CastRelationship;
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
                distinct split(elementId(r), ":")[1] as relationshipId,
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
              distinct split(elementId(r), ":")[1] as relationshipId,
              r.label as label,
              r.sourceId as sourceId,
              r.sourceHandle as sourceHandle,
              r.targetId as targetId,
              r.targetHandle as targetHandle
            """)
    Optional<CastRelationshipProjection> findBiconnection(Long sourceId, Long targetId);


    @Query("MATCH (n1:Cast{id: $sourceId})-[r:CONNECTION{uuid: $uuid}]->(n2:Cast{id: $targetId}) " +
            "WHERE n1.deletedAt IS NULL AND n2.deletedAt IS NULL " +
            "SET r.label = $label " +
            "SET r.sourceHandle = $sourceHandle " +
            "SET r.targetHandle = $targetHandle " +
            "RETURN r.uuid AS uuid, r.label AS label, r.sourceId AS sourceId, r.targetId AS targetId, " +
            "r.sourceHandle AS sourceHandle, r.targetHandle AS targetHandle")
    Optional<CastRelationship> updateDirectionalConnectionName(String uuid, Long sourceId, Long targetId, String label, String sourceHandle, String targetHandle);

    @Query("MATCH (n1:Cast{id: $sourceId})-[r:BI_CONNECTION{uuid: $uuid}]-(n2:Cast{id: $targetId}) " +
            "WHERE n1.deletedAt IS NULL AND n2.deletedAt IS NULL " +
            "SET r.label = $label " +
            "SET r.sourceHandle = $sourceHandle " +
            "SET r.targetHandle = $targetHandle " +
            "RETURN r.uuid AS uuid, r.label AS label, r.sourceId AS sourceId, r.targetId AS targetId, " +
            "r.sourceHandle AS sourceHandle, r.targetHandle AS targetHandle")
    Optional<CastRelationship> updateBidirectionalConnectionName(String uuid, Long sourceId, Long targetId, String label, String sourceHandle, String targetHandle);

    @Query("MATCH (n1:Cast)-[r:CONNECTION|BI_CONNECTION{uuid: $uuid}]-(n2:Cast) " +
            "DELETE r " +
            "RETURN count(DISTINCT r)")
    Integer deleteConnectionByUuid(String uuid);

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
			  id(c) as castId, c.name as name, c.gender as gender, c.imageUrl as imageUrl
			""")
    List<CastGraphNodeProjection> findGraphCastByProjectId(Long projectId);

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

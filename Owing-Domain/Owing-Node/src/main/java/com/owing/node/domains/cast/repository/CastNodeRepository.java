package com.owing.node.domains.cast.repository;

import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.CastRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CastNodeRepository extends Neo4jRepository<CastNode, Long> {

    @Query("""
            MATCH
                (c:Cast{deleted:false})
            WHERE
                id(c)=$castId
            RETURN
                c
            """)
    Optional<CastNode> findOneById(Long castId);

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

    @Query("MATCH (n1:Project{id: $projectId})-[r1:INCLUDED]->(n2:Cast) " +
            "WHERE n1.deletedAt IS NULL " +
                "AND n2.deletedAt IS NULL " +
            "RETURN n2")
    List<CastNode> findAllByProjectId(Long projectId);

//    @Query("MATCH (n1:Project{id: $projectId})-[r1:INCLUDED]->(n2:Cast)-[r2]-(n3:Cast) " +
//            "WHERE n1.deletedAt IS NULL " +
//                "AND n2.deletedAt IS NULL " +
//                "AND n3.deletedAt IS NULL " +
//            "RETURN DISTINCT " +
//                "type(r2) as type, r2.uuid AS uuid, r2.label AS label, r2.sourceId AS sourceId, " +
//                "r2.targetId AS targetId, r2.sourceHandle AS sourceHandle, r2.targetHandle AS targetHandle")
//    List<CastRelationshipInfoDto> findAllConnectionByProjectId(Long projectId);
//
//    @Query("MATCH (n1:Project{id: $projectId})-[r1:INCLUDED]->(n2:Cast) " +
//            "WHERE n1.deletedAt IS NULL " +
//            "AND n2.deletedAt IS NULL " +
//            "RETURN n2.id AS id, n2.name AS name, n2.gender AS gender")
//    List<CastSummaryDto> findAllSummaryByProjectId(Long projectId);
}

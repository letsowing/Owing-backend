package com.owing.node.folder.cast.repository;

import com.owing.node.common.repository.BaseFolderNodeRepository;
import com.owing.node.folder.cast.model.CastFolderNode;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface CastFolderNodeRepository extends BaseFolderNodeRepository<CastFolderNode> {

    @Query("""
            MATCH
              (p:Project{id:$projectId, deleted:false})-[r1:INCLUDE]->(cf:CastFolder{deleted:false})
            OPTIONAL MATCH
              (cf)-[r2:INCLUDE]->(c:Cast{deleted:false})
            WITH
              cf, r2, c
            ORDER BY
              cf.position, c.position
            RETURN
              cf, collect(r2), collect(c)
            """)
    List<CastFolderNode> findAllWithRelationshipByProjectId(Long projectId);

    /**
     * 단일 노드를 조회하는 메서드입니다. <p>
     * (Node)
     * @param folderId must not be {@literal null}.
     * @return
     */
    @Query("""
            MATCH
              (cf:CastFolder{deleted:false})
            WHERE
              id(cf)=$folderId
            RETURN
              cf
            """)
    Optional<CastFolderNode> findOneById(Long folderId);

    /**
     * 해당 노드, 직접적으로 연관된 관계까지 조회하는 메서드입니다.
     * (Node)-[relationship]-(Child)
     * @param folderId
     * @return
     */
    @Query("""
            MATCH
              (cf:CastFolder{deleted:false})-[r*1]-(n{deleted:false})
            WHERE
              id(cf)=$folderId
            RETURN
              cf, collect(distinct r), collect(distinct n)
            """)
    Optional<CastFolderNode> findOneWithRelationshipById(Long folderId);

    @Override
    @Query("""
        MATCH
          (p:Project{id:$projectId, deleted:false})-[r1:INCLUDE]->(f:CastFolder{deleted:false})
        ORDER BY
          f.position
        RETURN
          f
        """)
    List<CastFolderNode> findByParentId(Long parentId);

    @Override
    @Query("""
			MATCH
			  (t:CastFolder{deleted:false})
			WHERE
			  t.position > $position AND t.projectId = $projectId
			SET
			  t.position = t.position - 1
			""")
    void decrementPositionAfter(Long position, Long projectId);

    @Override
    @Query("""
			MATCH
			  (t:CastFolder{deleted:false})
			WHERE
			  t.position >= $start AND t.position <= $end AND t.projectId = $projectId
			SET
			  t.position = t.position - 1
			""")
    void decrementPositionBetween(Long start, Long end, Long projectId);

    @Override
    @Query("""
			MATCH
			  (t:CastFolder{deleted:false})
			WHERE
			  t.position >= $start AND t.position <= $end AND t.projectId = $projectId
			SET
			  t.position = t.position + 1
			""")
    void incrementPositionBetween(Long start, Long end, Long projectId);

    @Override
    @Query("""
			MATCH
			  (t:CastFolder{deleted:false})
			WHERE
			  t.projectId = $projectId
			RETURN
			  COALESCE(MAX(t.position), -1)
			""")
    Long getMaxPositionByParentId(Long parentId);

}

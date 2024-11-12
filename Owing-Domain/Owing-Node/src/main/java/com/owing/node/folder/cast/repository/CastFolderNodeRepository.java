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
              cf, r2, c, r1, p
            ORDER BY
              cf.position, c.position
            RETURN
              cf, collect(r2), collect(c), r1, p
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
              (cf:CastFolder{deleted:false})<-[r:INCLUDE]-(p:Project)
            WHERE
              id(cf)=$folderId
            RETURN
              cf, r, p
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

	/**
	 * projectId를 기준으로 하위 폴더를 모두 가져온는 메서드
	 * @param projectId
	 * @return
	 */
    @Override
    @Query("""
        MATCH
          (p:Project{id:$projectId, deleted:false})-[r:INCLUDE]->(f:CastFolder{deleted:false})
        ORDER BY
          f.position
        RETURN
          f, r, p
        """)
    List<CastFolderNode> findByParentId(Long projectId);

    @Override
    @Query("""
			MATCH
			  (p:Project{id:$projectId, deleted:false})-[r:INCLUDE]->(t:CastFolder{deleted:false})
			WHERE
			  t.position > $position
			SET
			  t.position = t.position - 1
			""")
    void decrementPositionAfter(Long position, Long projectId);

    @Override
    @Query("""
			MATCH
			  (p:Project{id:$projectId, deleted:false})-[r:INCLUDE]->(t:CastFolder{deleted:false})
			WHERE
			  t.position >= $start AND t.position <= $end
			SET
			  t.position = t.position - 1
			""")
    void decrementPositionBetween(Long start, Long end, Long projectId);

    @Override
    @Query("""
			MATCH
			  (p:Project{id:$projectId, deleted:false})-[r:INCLUDE]->(t:CastFolder{deleted:false})
			WHERE
			  t.position >= $start AND t.position <= $end
			SET
			  t.position = t.position + 1
			""")
    void incrementPositionBetween(Long start, Long end, Long projectId);

    @Override
    @Query("""
			MATCH
				(p:Project{deleted:false})-[r:INCLUDE]->(t:CastFolder{deleted:false})
			WHERE
				p.id = $projectId
			RETURN
				COALESCE(MAX(t.position), -1)
			""")
	Long getMaxPositionByParentId(Long projectId);

}

package com.owing.node.common.repository;

import java.util.List;

import com.owing.core.dnd.folder.repository.BaseFolderRepository;
import com.owing.node.common.model.BaseFolderNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseFolderNodeRepository<T extends BaseFolderNode> extends BaseFolderRepository<T>, Neo4jRepository<T, Long> {
	@Query("""
        MATCH
          (p:Project{id:$projectId, deleted:false})-[r1:INCLUDE]->(f:`:#{literal(#folderNodeName)}`{deleted:false})
        ORDER BY
          f.position
        RETURN
          f
        """)
	List<T> findByParentId(Long projectId, String folderNodeName);

	List<T> findAllByProjectIdOrderByPositionAsc(Long projectId);

//	@Query("update #{#entityName} T set T.position = T.position - 1 where T.position > :position and T.projectId = :projectId")
	@Query("""
			MATCH
			  (t:`:#{literal(#folderNodeName)}`)
			WHERE
			  t.position > $position AND t.projectId = $projectId
			SET
			  t.position = t.position - 1
			""")
	void decrementPositionAfter(Long position, Long projectId, String folderNodeName);

//	@Query("SELECT COALESCE(MAX(T.position), '-1') FROM #{#entityName} T WHERE T.projectId = :projectId")
	@Query("""
			MATCH
			  (t:`:#{literal(#folderNodeName)}`)
			WHERE
			  t.projectId = $projectId
			RETURN
			  COALESCE(MAX(t.position), -1)
			""")
	Long getMaxPositionByParentId(Long projectId, String folderNodeName);

//	@Query("update  #{#entityName} T set T.position = T.position - 1 where T.position between :start and :end and T.projectId = :projectId")
	@Query("""
			MATCH
			  (t:`:#{literal(#folderNodeName)}`)
			WHERE
			  t.position >= $start AND t.position <= $end AND t.projectId = $projectId
			SET
			  t.position = t.position - 1
			""")
	void decrementPositionBetween(Long start, Long end, Long projectId, String folderNodeName);

//	@Query("update #{#entityName} T set T.position = T.position + 1 where T.position between :start and :end and T.projectId = :projectId")
	@Query("""
			MATCH
			  (t:`:#{literal(#folderNodeName)}`)
			WHERE
			  t.position >= $start AND t.position <= $end AND t.projectId = $projectId
			SET
			  t.position = t.position + 1
			""")
	void incrementPositionBetween(Long start, Long end, Long projectId, String folderNodeName);

}

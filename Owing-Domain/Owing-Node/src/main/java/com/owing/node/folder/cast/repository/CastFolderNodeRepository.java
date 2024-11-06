package com.owing.node.folder.cast.repository;

import com.owing.node.folder.cast.model.CastFolderNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CastFolderNodeRepository extends Neo4jRepository<CastFolderNode, Long> {

    @Query("""
            MATCH
              (p:Project{id:$projectId, deleted:false})-[r1:INCLUDE]
              ->(cf1:CastFolder{deleted:false})-[r:INCLUDE]->(c1:Cast{deleted:false})
            RETURN
              cf1, collect(r), collect(c1)
            """)
    List<CastFolderNode> findAllByProjectId(Long projectId);

}

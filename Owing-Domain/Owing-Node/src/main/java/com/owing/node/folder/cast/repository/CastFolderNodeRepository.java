package com.owing.node.folder.cast.repository;

import com.owing.node.folder.cast.model.CastFolderNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CastFolderNodeRepository extends Neo4jRepository<CastFolderNode, Long> {
}

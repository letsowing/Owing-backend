package com.owing.node.common.repository;

import com.owing.core.dnd.folder.repository.DndFolderRepository;
import com.owing.node.common.model.DndFolderNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DndFolderNodeRepository<T extends DndFolderNode> extends DndFolderRepository<T>, Neo4jRepository<T, Long> {

}

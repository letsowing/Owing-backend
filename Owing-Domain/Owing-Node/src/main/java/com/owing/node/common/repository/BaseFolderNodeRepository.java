package com.owing.node.common.repository;

import com.owing.core.dnd.folder.repository.BaseFolderRepository;
import com.owing.node.common.model.BaseFolderNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseFolderNodeRepository<T extends BaseFolderNode> extends BaseFolderRepository<T>, Neo4jRepository<T, Long> {

}

package com.owing.node.common.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.owing.core.dnd.service.shift.repository.FolderShiftRepository;
import com.owing.node.common.model.DndFolderNode;

@NoRepositoryBean
public interface DndFolderNodeRepository<T extends DndFolderNode> extends FolderShiftRepository<T>, Neo4jRepository<T, Long> {

}

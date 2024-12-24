package com.owing.node.common.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.owing.core.dnd.file.model.DndFile;
import com.owing.core.dnd.file.repository.DndFileRepository;

@NoRepositoryBean
public interface DndFileNodeRepository<T extends DndFile> extends DndFileRepository<T>, Neo4jRepository<T, Long> {
}

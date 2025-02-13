package com.owing.node.common.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.owing.core.dnd.model.DndFile;
import com.owing.core.dnd.service.shift.repository.FileShiftRepository;

@NoRepositoryBean
public interface DndFileNodeRepository<T extends DndFile> extends FileShiftRepository<T>, Neo4jRepository<T, Long> {
}

package com.owing.node.common.repository;

import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.file.repository.BaseFileRepository;
import com.owing.core.dnd.folder.model.BaseFolder;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseFileNodeRepository<T extends BaseFile<F>, F extends BaseFolder>
        extends BaseFileRepository<T, F>, Neo4jRepository<T, Long> {
}

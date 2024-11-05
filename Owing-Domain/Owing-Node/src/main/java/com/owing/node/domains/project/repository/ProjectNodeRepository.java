package com.owing.node.domains.project.repository;

import com.owing.node.domains.project.model.ProjectNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ProjectNodeRepository extends Neo4jRepository<ProjectNode, Long> {
}

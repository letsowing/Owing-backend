package com.owing.node.domains.story.repository;


import com.owing.node.domains.story.model.StoryNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface StoryNodeRepository extends Neo4jRepository<StoryNode, Long> {
}

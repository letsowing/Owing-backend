package com.owing.node.folder.story.repository;

import com.owing.node.folder.story.model.StoryFolderNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface StoryFolderNodeRepository extends Neo4jRepository<StoryFolderNode, Long> {
}

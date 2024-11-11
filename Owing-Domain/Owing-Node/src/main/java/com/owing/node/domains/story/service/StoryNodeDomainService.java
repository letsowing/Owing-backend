package com.owing.node.domains.story.service;

import com.owing.common.annotation.DomainService;
import com.owing.node.domains.story.adapter.StoryNodeAdapter;
import com.owing.node.domains.story.model.StoryNode;
import com.owing.node.domains.story.repository.StoryNodeRepository;
import com.owing.node.folder.story.model.StoryFolderNode;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryNodeDomainService {

    private final StoryNodeRepository storyNodeRepository;
    private final StoryNodeAdapter storyNodeAdapter;

    @Transactional
    public void createStoryNode(StoryNode storyNode, StoryFolderNode storyFolderNode) {
        storyNode.connectFolder(storyFolderNode);
        storyNodeRepository.save(storyNode);
    }

    @Transactional
    public void deleteStoryNode(Long storyId) {
        StoryNode storyNode = storyNodeAdapter.findById(storyId);
        storyNode.delete();
        storyNodeRepository.save(storyNode);
    }
}

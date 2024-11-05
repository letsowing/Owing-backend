package com.owing.node.domains.story.service;

import com.owing.common.annotation.DomainService;
import com.owing.node.domains.story.adaptor.StoryNodeAdaptor;
import com.owing.node.domains.story.model.StoryNode;
import com.owing.node.domains.story.repository.StoryNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryNodeDomainService {

    private final StoryNodeRepository storyNodeRepository;
    private final StoryNodeAdaptor storyNodeAdaptor;

        @Transactional
    public void createStoryNode(StoryNode storyNode) {
        storyNodeRepository.save(storyNode);
    }

    @Transactional
    public void deleteStoryNode(Long storyId) {
        StoryNode storyNode = storyNodeAdaptor.findById(storyId);
        storyNode.delete();
        storyNodeRepository.save(storyNode);
    }
}

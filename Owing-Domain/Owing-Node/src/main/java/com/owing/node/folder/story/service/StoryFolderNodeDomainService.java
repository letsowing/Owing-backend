package com.owing.node.folder.story.service;

import com.owing.common.annotation.DomainService;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.story.adaptor.StoryFolderNodeAdaptor;
import com.owing.node.folder.story.model.StoryFolderNode;
import com.owing.node.folder.story.repository.StoryFolderNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryFolderNodeDomainService {

    private final StoryFolderNodeRepository storyFolderNodeRepository;
    private final StoryFolderNodeAdaptor storyFolderNodeAdaptor;

    @Transactional
    public void createStoryFolderNode(StoryFolderNode storyFolderNode, ProjectNode projectNode) {
        storyFolderNode.connectProject(projectNode);
        storyFolderNodeRepository.save(storyFolderNode);
    }

    @Transactional
    public void deleteStoryFolderNode(Long storyFolderId) {
        StoryFolderNode storyFolderNode = storyFolderNodeAdaptor.findById(storyFolderId);
        storyFolderNode.delete();
        storyFolderNodeRepository.save(storyFolderNode);
    }
}

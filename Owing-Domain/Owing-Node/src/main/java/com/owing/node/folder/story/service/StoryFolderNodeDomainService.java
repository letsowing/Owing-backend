package com.owing.node.folder.story.service;

import com.owing.common.annotation.DomainService;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.story.adaptor.StoryFolderNodeAdaptor;
import com.owing.node.folder.story.error.code.StoryFolderNodeErrorCode;
import com.owing.node.folder.story.error.exception.StoryFolderNodeRelationshipException;
import com.owing.node.folder.story.model.StoryFolderNode;
import com.owing.node.folder.story.repository.StoryFolderNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryFolderNodeDomainService {

    private final StoryFolderNodeRepository storyFolderNodeRepository;
    private final StoryFolderNodeAdaptor storyFolderNodeAdaptor;

    @Transactional
    public void createStoryFolderNode(StoryFolderNode storyFolderNode, ProjectNode projectNode) {
        StoryFolderNode savedNode = storyFolderNodeRepository.save(storyFolderNode);
        connectProject(savedNode, projectNode);
    }

    @Transactional
    public void deleteStoryFolderNode(Long storyFolderId) {
        StoryFolderNode storyFolderNode = storyFolderNodeAdaptor.findById(storyFolderId);
        storyFolderNode.delete();
        storyFolderNodeRepository.save(storyFolderNode);
    }

    @Transactional
    public void connectProject(StoryFolderNode storyFolderNode, ProjectNode projectNode) {
        if (ObjectUtils.isEmpty(storyFolderNode.getDeleted())) {
            throw StoryFolderNodeRelationshipException.of(
                    StoryFolderNodeErrorCode.RELATIONSHIP_ALREADY_EXISTS,
                    "StoryFolder Id: %d, Connected Project Id: %d, Requested Project Id: %d"
                            .formatted(storyFolderNode.getId(), storyFolderNode.getProject().getId(), projectNode.getId())
            );
        }

        storyFolderNode.connectProject(projectNode);
        storyFolderNodeRepository.save(storyFolderNode);
    }
}

package com.owing.api.project.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.cast.model.mapper.CastFolderNodeMapper;
import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.api.project.model.mapper.ProjectMapper;
import com.owing.api.project.model.mapper.ProjectNodeMapper;
import com.owing.api.story.model.mapper.StoryFolderMapper;
import com.owing.api.universe.model.mapper.UniverseFolderMapper;
import com.owing.common.annotation.UseCase;
import com.owing.common.util.MemberUtils;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.service.ProjectDomainService;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryFolderService;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseFolderService;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.domains.project.service.ProjectNodeDomainService;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderNodeService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateProjectUseCase {
    private final MemberUtils memberUtils;
    private final ProjectDomainService projectDomainService;
    private final ProjectMapper projectMapper;

    private final ProjectNodeDomainService projectNodeDomainService;
    private final ProjectNodeMapper projectNodeMapper;
    private final CastFolderNodeMapper castFolderNodeMapper;
    private final CastFolderNodeService castFolderNodeDomainService;
    private final StoryFolderMapper storyFolderMapper;
    private final StoryFolderService storyFolderDomainService;
    private final UniverseFolderMapper universeFolderMapper;
    private final UniverseFolderService universeFolderDomainService;

    @Transactional("jpaTransactionManager")
    public ProjectShortInfoResponse execute(AddProjectRequest addProjectRequest) {
        Member memberReference = memberUtils.getCurrentMemberReference();

        Project savedProject = createEntity(addProjectRequest, memberReference);
        ProjectNode savedProjectNode = createNode(savedProject);

        initDomainFolder(savedProject, savedProjectNode);

        return projectMapper.toShortInfoResponse(savedProject);
    }

    private Project createEntity(AddProjectRequest addProjectRequest, Member memberReference) {
        Project project = projectMapper.toEntity(addProjectRequest, memberReference);
        return projectDomainService.createProject(project);
    }

    private ProjectNode createNode(Project savedProject) {
        ProjectNode projectNode = projectNodeMapper.toNode(savedProject.getId());
        return projectNodeDomainService.createProjectNode(projectNode);
    }

    private void initDomainFolder(Project savedProject, ProjectNode savedProjectNode) {
        AddFolderRequest initialFolderRequest = AddFolderRequest.initialFolder(savedProject.getId());

        // JPA Folder
        StoryFolder initialStoryFolder = storyFolderMapper.toEntity(initialFolderRequest);
        storyFolderDomainService.create(initialStoryFolder);

        UniverseFolder initialUniverseFolder = universeFolderMapper.toEntity(initialFolderRequest);
        universeFolderDomainService.create(initialUniverseFolder);

        // Neo4j Folder
        CastFolderNode initialCastFolder = castFolderNodeMapper.toEntity(initialFolderRequest);
        initialCastFolder.connectProject(savedProjectNode);
        castFolderNodeDomainService.create(initialCastFolder);
    }

}

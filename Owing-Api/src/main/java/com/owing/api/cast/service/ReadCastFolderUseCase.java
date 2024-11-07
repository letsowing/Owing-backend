package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.response.CastFolderResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.project.adaptor.ProjectNodeAdaptor;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.cast.adaptor.CastFolderNodeAdaptor;
import com.owing.node.folder.cast.model.CastFolderNode;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ReadCastFolderUseCase {

    private final ProjectNodeAdaptor projectNodeAdaptor;
    private final CastFolderNodeAdaptor castFolderNodeAdaptor;
    private final CastNodeMapper castNodeMapper;

    public List<CastFolderResponse> executeGetList(Long projectId) {
        ProjectNode projectNode = projectNodeAdaptor.findById(projectId);
        List<CastFolderNode> castFolderNodeList = castFolderNodeAdaptor.findAllWithRelationshipByProjectId(projectNode.getId());
        return getSortedCastFolderResponse(castFolderNodeList);
    }

    public CastFolderResponse executeGetOne(Long folderId) {
        CastFolderNode castFolderNode = castFolderNodeAdaptor.findOneWithRelationshipById(folderId);
        return getSortedCastFolderResponse(List.of(castFolderNode)).getFirst();
    }

    private List<CastFolderResponse> getSortedCastFolderResponse(List<CastFolderNode> castFolderNodeList) {
        return castFolderNodeList.stream()
                .sorted(Comparator.comparingLong(CastFolderNode::getPosition))
                .map(folder -> new CastFolderResponse(
                        folder.getId(),
                        folder.getName(),
                        folder.getDescription(),
                        folder.getCast().stream()
                                .sorted(Comparator.comparingLong(CastNode::getPosition))
                                .map(castNodeMapper::toFileResponse)
                                .toList()
                ))
                .toList();
    }
}

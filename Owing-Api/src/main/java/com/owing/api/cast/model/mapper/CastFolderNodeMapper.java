package com.owing.api.cast.model.mapper;

import com.owing.api.cast.model.dto.response.CastFileResponse;
import com.owing.api.cast.model.dto.response.CastFolderResponse;
import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderTitleRequest;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.common.annotation.Mapper;
import com.owing.node.folder.cast.model.CastFolderNode;

import java.util.List;

@Mapper
public class CastFolderNodeMapper extends BaseFolderMapper<CastFolderNode> {

    public CastFolderNode toEntity(String name, String description) {
        return new CastFolderNode(name, description);
    }

    @Override
    public CastFolderNode toEntity(AddFolderRequest addDndRequest) {
        return new CastFolderNode(addDndRequest.name(), addDndRequest.description());
    }

    @Override
    public CastFolderNode toEntity(UpdateFolderTitleRequest updateDndRequest) {
        return CastFolderNode.builder()
                .name(updateDndRequest.name())
                .build();
    }

    @Override
    public FolderInfoResponse toInfoResponse(CastFolderNode castFolderNode) {
        return FolderInfoResponse.from(castFolderNode);
    }

    public CastFolderResponse toFolderResponse(CastFolderNode castFolderNode, List<CastFileResponse> castFileResponseList) {
        return new CastFolderResponse(
                castFolderNode.getId(),
                castFolderNode.getName(),
                castFolderNode.getDescription(),
                castFileResponseList
        );
    }
}

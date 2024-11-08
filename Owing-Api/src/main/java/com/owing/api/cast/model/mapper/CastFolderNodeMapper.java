package com.owing.api.cast.model.mapper;

import com.owing.api.cast.model.dto.response.CastFileResponse;
import com.owing.api.cast.model.dto.response.CastFolderInfoResponse;
import com.owing.api.cast.model.dto.response.CastFolderResponse;
import com.owing.common.annotation.Mapper;
import com.owing.node.folder.cast.model.CastFolderNode;

import java.util.List;

@Mapper
public class CastFolderNodeMapper {

    public CastFolderNode toEntity(String name, String description) {
        return new CastFolderNode(name, description);
    }

    public CastFolderInfoResponse toInfoResponse(CastFolderNode castFolderNode) {
        return new CastFolderInfoResponse(
                castFolderNode.getId(),
                castFolderNode.getName(),
                castFolderNode.getDescription()
        );
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

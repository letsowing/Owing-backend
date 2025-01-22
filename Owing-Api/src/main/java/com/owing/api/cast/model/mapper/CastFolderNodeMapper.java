package com.owing.api.cast.model.mapper;

import java.util.List;

import com.owing.api.cast.model.dto.response.CastFileResponse;
import com.owing.api.cast.model.dto.response.CastFolderDropdownItemResponse;
import com.owing.api.cast.model.dto.response.CastFolderResponse;
import com.owing.api.dnd.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.model.dto.request.UpdateFolderTitleRequest;
import com.owing.api.dnd.model.mapper.BaseFolderMapper;
import com.owing.common.annotation.Mapper;
import com.owing.node.folder.cast.model.CastFolderNode;

@Mapper
public class CastFolderNodeMapper extends BaseFolderMapper<CastFolderNode> {

    public CastFolderNode toEntity(String name) {
        return CastFolderNode.builder()
                .name(name)
                .build();
    }

    @Override
    public CastFolderNode toEntity(AddFolderRequest addDndRequest) {
        return this.toEntity(addDndRequest.name());
    }

    @Override
    public CastFolderNode toEntity(UpdateFolderTitleRequest updateDndRequest) {
        return this.toEntity(updateDndRequest.name());
    }

    public CastFolderResponse toFolderResponse(CastFolderNode castFolderNode, List<CastFileResponse> castFileResponseList) {
        return new CastFolderResponse(
                castFolderNode.getId(),
                castFolderNode.getName(),
                castFolderNode.getDescription(),
                castFileResponseList
        );
    }

    public CastFolderDropdownItemResponse toDropdownItemResponse(CastFolderNode castFolderNode) {
        return new CastFolderDropdownItemResponse(castFolderNode.getId(), castFolderNode.getName());
    }

    public List<CastFolderDropdownItemResponse> toDropdownListResponse(List<CastFolderNode> castFolderNodeList) {
        return castFolderNodeList.stream()
                .map(this::toDropdownItemResponse)
                .toList();
    }
}

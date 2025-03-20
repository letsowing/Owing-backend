package com.owing.api.cast.model.mapper;

import java.util.List;

import com.owing.api.cast.model.dto.response.CastFileResponse;
import com.owing.api.cast.model.dto.response.CastFolderDropdownItemResponse;
import com.owing.api.cast.model.dto.response.CastFolderResponse;
import com.owing.api.dnd.dto.common.response.CommonFolderInfoListResponse;
import com.owing.api.dnd.dto.common.response.CommonFolderInfoResponse;
import com.owing.api.dnd.dto.request.AddFolderRequest;
import com.owing.api.dnd.dto.request.UpdateFolderNameRequest;
import com.owing.api.dnd.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.dto.response.FolderInfoResponse;
import com.owing.api.dnd.mapper.DndFolderMapper;
import com.owing.common.annotation.Mapper;
import com.owing.node.folder.cast.model.CastFolderNode;

@Mapper
public class CastFolderNodeMapper implements DndFolderMapper<CastFolderNode> {

    public CastFolderNode toEntity(String name) {
        return CastFolderNode.builder()
                .name(name)
                .build();
    }

    @Override
    public FolderInfoResponse toInfoResponse(CastFolderNode entity) {
        return CommonFolderInfoResponse.from(entity);
    }

    @Override
    public FolderInfoListResponse toListResponse(List<CastFolderNode> entity) {
        return CommonFolderInfoListResponse.from(entity);
    }

    @Override
    public CastFolderNode toEntity(AddFolderRequest addDndRequest) {
        return this.toEntity(addDndRequest.name());
    }

    public CastFolderNode toEntity(UpdateFolderNameRequest updateDndRequest) {
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

package com.owing.api.universe.model.mapper;

import java.util.List;

import com.owing.api.dnd.dto.request.AddFolderRequest;
import com.owing.api.dnd.dto.request.UpdateFolderNameRequest;
import com.owing.api.dnd.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.mapper.DndFolderMapper;
import com.owing.api.universe.model.dto.response.UniverseFolderInfoListResponse;
import com.owing.api.universe.model.dto.response.UniverseFolderInfoResponse;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class UniverseFolderMapper implements DndFolderMapper<UniverseFolder> {

	@Override
	public UniverseFolder toEntity(AddFolderRequest addDndRequest) {
		return UniverseFolder.basicBuilder()
			.name(addDndRequest.name())
			.projectId(addDndRequest.projectId())
			.build();
	}

	public UniverseFolder toEntity(UpdateFolderNameRequest updateDndRequest) {
		return UniverseFolder.basicBuilder()
			.name(updateDndRequest.name())
			.build();
	}

	@Override
	public UniverseFolderInfoResponse toInfoResponse(UniverseFolder entity) {
		return UniverseFolderInfoResponse.from(entity);
	}

	@Override
	public FolderInfoListResponse toListResponse(List<UniverseFolder> entity) {
		return UniverseFolderInfoListResponse.from(entity);
	}

}

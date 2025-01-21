package com.owing.api.universe.model.mapper;

import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderTitleRequest;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.universe.model.dto.response.UniverseFolderInfoResponse;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class UniverseFolderMapper extends BaseFolderMapper<UniverseFolder> {

	@Override
	public UniverseFolder toEntity(AddFolderRequest addDndRequest) {
		return UniverseFolder.basicBuilder()
			.name(addDndRequest.name())
			.projectId(addDndRequest.projectId())
			.build();
	}

	@Override
	public UniverseFolder toEntity(UpdateFolderTitleRequest updateDndRequest) {
		return UniverseFolder.basicBuilder()
			.name(updateDndRequest.name())
			.build();
	}

	@Override
	public UniverseFolderInfoResponse toInfoResponse(UniverseFolder entity) {
		return UniverseFolderInfoResponse.from(entity);
	}

}

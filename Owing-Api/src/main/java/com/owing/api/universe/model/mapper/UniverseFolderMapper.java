package com.owing.api.universe.model.mapper;

import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderTitleRequest;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class UniverseFolderMapper extends BaseFolderMapper<UniverseFolder> {

	@Override
	public UniverseFolder toEntity(AddFolderRequest addDndRequest) {
		return UniverseFolder.builder()
			.name(addDndRequest.name())
			.description(addDndRequest.description())
			.projectId(addDndRequest.projectId())
			.build();
	}

	@Override
	public UniverseFolder toEntity(UpdateFolderTitleRequest updateDndRequest) {
		return UniverseFolder.builder()
			.name(updateDndRequest.name())
			.build();
	}

}

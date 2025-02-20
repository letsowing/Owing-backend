package com.owing.api.story.model.mapper;

import java.util.List;

import com.owing.api.dnd.dto.common.response.CommonFolderInfoResponse;
import com.owing.api.dnd.dto.common.response.CommonFolderInfoListResponse;
import com.owing.api.dnd.dto.request.AddFolderRequest;
import com.owing.api.dnd.dto.request.UpdateFolderNameRequest;
import com.owing.api.dnd.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.dto.response.FolderInfoResponse;
import com.owing.api.dnd.mapper.DndFolderMapper;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.story.model.StoryFolder;

@Mapper
public class StoryFolderMapper implements DndFolderMapper<StoryFolder> {
	@Override
	public FolderInfoResponse toInfoResponse(StoryFolder entity) {
		return CommonFolderInfoResponse.from(entity);
	}

	@Override
	public FolderInfoListResponse toListResponse(List<StoryFolder> entity) {
		return CommonFolderInfoListResponse.from(entity);
	}

	public StoryFolder toEntity(AddFolderRequest addFolderRequest) {
		return StoryFolder.builder()
			.name(addFolderRequest.name())
			.projectId(addFolderRequest.projectId())
			.build();
	}

	public StoryFolder toEntity(UpdateFolderNameRequest updateFolderRequest) {
		return StoryFolder.builder()
			.name(updateFolderRequest.name())
			.build();
	}
}

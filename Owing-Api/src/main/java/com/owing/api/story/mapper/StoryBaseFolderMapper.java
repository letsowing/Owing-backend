package com.owing.api.story.mapper;

import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderRequest;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.story.model.StoryFolder;

@Mapper
public class StoryBaseFolderMapper extends BaseFolderMapper<StoryFolder> {
	public StoryFolder toEntity(AddFolderRequest addFolderRequest) {
		return StoryFolder.builder()
			.name(addFolderRequest.name())
			.description(addFolderRequest.description())
			.projectId(addFolderRequest.projectId())
			.build();
	}

	public StoryFolder toEntity(UpdateFolderRequest updateFolderRequest) {
		return StoryFolder.builder()
			.name(updateFolderRequest.name())
			.description(updateFolderRequest.description())
			.build();
	}
}

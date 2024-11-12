package com.owing.api.story.model.mapper;

import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderTitleRequest;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.story.model.StoryFolder;

@Mapper
public class StoryFolderMapper extends BaseFolderMapper<StoryFolder> {
	public StoryFolder toEntity(AddFolderRequest addFolderRequest) {
		return StoryFolder.builder()
			.name(addFolderRequest.name())
			.projectId(addFolderRequest.projectId())
			.build();
	}

	public StoryFolder toEntity(UpdateFolderTitleRequest updateFolderRequest) {
		return StoryFolder.builder()
			.name(updateFolderRequest.name())
			.build();
	}
}

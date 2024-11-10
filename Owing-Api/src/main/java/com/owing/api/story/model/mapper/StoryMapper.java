package com.owing.api.story.model.mapper;

import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileRequest;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;

@Mapper
public class StoryMapper extends BaseFileMapper<Story, StoryFolder> {
	public Story toEntity(AddFileRequest addDndRequest, StoryFolder folder) {
		return Story.builder()
			.title(addDndRequest.title())
			.description(addDndRequest.description())
			.folder(folder)
			.build();
	}

	public Story toEntity(AddFileRequest addDndRequest) {
		return Story.builder()
			.title(addDndRequest.title())
			.description(addDndRequest.description())
			.build();
	}

	public Story toEntity(UpdateFileRequest updateDndRequest) {
		return Story.builder()
			.title(updateDndRequest.title())
			.description(updateDndRequest.description())
			.build();
	}
}

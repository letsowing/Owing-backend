package com.owing.api.story.model.mapper;

import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileTitleRequest;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.story.model.dto.response.StoryInfoResponse;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;

@Mapper
public class StoryMapper extends BaseFileMapper<Story, StoryFolder> {
	public Story toEntity(AddFileRequest addDndRequest, StoryFolder folder) {
		return Story.builder()
			.name(addDndRequest.name())
			.folder(folder)
			.build();
	}

	public Story toEntity(AddFileRequest addDndRequest) {
		return Story.builder()
			.name(addDndRequest.name())
			.build();
	}

	public Story toEntity(UpdateFileTitleRequest updateDndRequest) {
		return Story.builder()
			.name(updateDndRequest.name())
			.build();
	}
}

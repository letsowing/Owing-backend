package com.owing.entity.domains.story.service;

import org.springframework.stereotype.Component;

import com.owing.core.dnd.service.shift.FileShiftDndService;
import com.owing.core.dnd.service.shift.adapter.FileShiftAdapter;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StoryDndService extends FileShiftDndService<Story> {
	private final StoryAdapter dndAdapter;

	@Override
	protected FileShiftAdapter<Story> dndAdapter() {
		return dndAdapter;
	}
}

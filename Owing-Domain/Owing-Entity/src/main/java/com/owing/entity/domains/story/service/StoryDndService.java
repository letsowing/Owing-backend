package com.owing.entity.domains.story.service;

import org.springframework.stereotype.Component;

import com.owing.core.dnd.service.shift.DndShiftAdapter;
import com.owing.core.dnd.service.shift.DndShiftService;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StoryDndService extends DndShiftService<Story> {
	private final StoryAdapter dndAdapter;

	@Override
	protected DndShiftAdapter<Story> dndAdapter() {
		return dndAdapter;
	}
}

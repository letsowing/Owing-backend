package com.owing.entity.domains.story.service;

import org.springframework.stereotype.Component;

import com.owing.core.dnd.orderStrategy.shift.FolderShiftOrderingStrategy;
import com.owing.core.dnd.orderStrategy.shift.adapter.FolderShiftAdapter;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StoryFolderShiftOrderingStrategy extends FolderShiftOrderingStrategy<StoryFolder> {
	private final StoryFolderAdapter dndAdapter;

	@Override
	protected FolderShiftAdapter<StoryFolder> dndAdapter() {
		return dndAdapter;
	}
}

package com.owing.entity.domains.story.service;

import org.springframework.stereotype.Component;

import com.owing.core.dnd.folder.adapter.DndFolderAdapter;
import com.owing.core.dnd.orderStrategy.shift.FolderShiftOrderingStrategy;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StoryFolderShiftOrderingStrategy extends FolderShiftOrderingStrategy<StoryFolder> {
	private final StoryFolderAdapter dndAdapter;

	@Override
	protected DndFolderAdapter<StoryFolder> dndAdapter() {
		return dndAdapter;
	}
}

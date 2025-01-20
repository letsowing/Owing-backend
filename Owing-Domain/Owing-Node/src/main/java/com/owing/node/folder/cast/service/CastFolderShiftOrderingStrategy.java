package com.owing.node.folder.cast.service;

import org.springframework.stereotype.Component;

import com.owing.core.dnd.orderStrategy.shift.FolderShiftOrderingStrategy;
import com.owing.core.dnd.orderStrategy.shift.adapter.FolderShiftAdapter;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CastFolderShiftOrderingStrategy extends FolderShiftOrderingStrategy<CastFolderNode> {

	private final CastFolderNodeAdapter adapter;

	@Override
	protected FolderShiftAdapter<CastFolderNode> dndAdapter() {
		return adapter;
	}
}

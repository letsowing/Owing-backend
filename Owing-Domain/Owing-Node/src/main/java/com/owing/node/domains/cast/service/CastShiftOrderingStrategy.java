package com.owing.node.domains.cast.service;

import org.springframework.stereotype.Component;

import com.owing.core.dnd.file.adapter.DndFileAdapter;
import com.owing.core.dnd.orderStrategy.shift.FileShiftOrderingStrategy;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CastShiftOrderingStrategy extends FileShiftOrderingStrategy<CastNode> {

	private final CastNodeAdapter castNodeAdapter;

	@Override
	protected DndFileAdapter<CastNode> dndAdapter() {
		return castNodeAdapter;
	}
}
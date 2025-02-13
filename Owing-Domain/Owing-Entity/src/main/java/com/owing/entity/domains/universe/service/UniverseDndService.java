package com.owing.entity.domains.universe.service;

import org.springframework.stereotype.Component;

import com.owing.core.dnd.service.shift.FileShiftDndService;
import com.owing.core.dnd.service.shift.adapter.FileShiftAdapter;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.model.Universe;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniverseDndService extends FileShiftDndService<Universe> {
	private final UniverseAdapter dndAdapter;

	@Override
	protected FileShiftAdapter<Universe> dndAdapter() {
		return dndAdapter;
	}
}

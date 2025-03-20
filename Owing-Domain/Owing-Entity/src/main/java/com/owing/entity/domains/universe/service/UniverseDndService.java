package com.owing.entity.domains.universe.service;

import org.springframework.stereotype.Component;

import com.owing.core.dnd.service.shift.DndShiftAdapter;
import com.owing.core.dnd.service.shift.DndShiftService;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.model.Universe;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniverseDndService extends DndShiftService<Universe> {
	private final UniverseAdapter dndAdapter;

	@Override
	protected DndShiftAdapter<Universe> dndAdapter() {
		return dndAdapter;
	}
}

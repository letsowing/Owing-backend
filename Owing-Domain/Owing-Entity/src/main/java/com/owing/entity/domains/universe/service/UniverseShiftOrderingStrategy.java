package com.owing.entity.domains.universe.service;

import org.springframework.stereotype.Component;

import com.owing.core.dnd.orderStrategy.shift.FileShiftOrderingStrategy;
import com.owing.core.dnd.orderStrategy.shift.adapter.FileShiftAdapter;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.model.Universe;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniverseShiftOrderingStrategy extends FileShiftOrderingStrategy<Universe> {
	private final UniverseAdapter dndAdapter;

	@Override
	protected FileShiftAdapter<Universe> dndAdapter() {
		return dndAdapter;
	}
}

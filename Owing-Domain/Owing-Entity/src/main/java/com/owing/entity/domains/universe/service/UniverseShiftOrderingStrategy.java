package com.owing.entity.domains.universe.service;

import org.springframework.stereotype.Component;

import com.owing.core.dnd.base.orderStrategy.shift.FileShiftOrderingStrategy;
import com.owing.entity.domains.universe.adaptor.UniverseAdaptor;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.repository.UniverseRepository;

@Component
public class UniverseShiftOrderingStrategy extends FileShiftOrderingStrategy<Universe, UniverseFolder> {
	public UniverseShiftOrderingStrategy(UniverseAdaptor dndAdapter,
		UniverseRepository dndRepository) {
		super(dndAdapter, dndRepository);
	}
}

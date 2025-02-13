package com.owing.entity.domains.universe.adapter;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.service.shift.adapter.FileShiftAdapter;
import com.owing.core.dnd.service.shift.repository.FileShiftRepository;
import com.owing.entity.domains.universe.error.UniverseErrorCode;
import com.owing.entity.domains.universe.error.exception.UniverseNotFoundException;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.dto.UniverseInfo;
import com.owing.entity.domains.universe.repository.UniverseDeletedRepository;
import com.owing.entity.domains.universe.repository.UniverseRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class UniverseAdapter extends FileShiftAdapter<Universe> {
	private final UniverseRepository universeRepository;
	private final UniverseDeletedRepository universeDeletedRepository;

	@Override
	protected FileShiftRepository<Universe> dndRepository() {
		return universeRepository;
	}

	public Universe findById(Long universeId) {
		return universeRepository.findById(universeId)
			.orElseThrow(() -> UniverseNotFoundException.of(UniverseErrorCode.UNIVERSE_NOT_FOUND));
	}

	public List<Universe> findByProjectId(Long projectId) {
		return universeRepository.findByFolder_ProjectId(projectId);
	}

	public UniverseInfo findDeletedById(Long itemId) {
		return universeDeletedRepository.findDeletedById(itemId);
	}

	public String findImageUrlById(Long id) {
		return universeRepository.findImageUrlById(id);
	}

	@Transactional("jpaTransactionManager")
	public void restoreById(Long aLong) {
		universeRepository.restoreById(aLong);
	}
}

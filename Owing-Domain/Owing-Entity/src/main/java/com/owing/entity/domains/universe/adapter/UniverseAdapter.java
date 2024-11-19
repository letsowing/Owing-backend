package com.owing.entity.domains.universe.adapter;

import java.util.List;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.file.adapter.BaseFileAdapter;
import com.owing.core.dnd.file.repository.BaseFileRepository;
import com.owing.entity.domains.universe.error.UniverseErrorCode;
import com.owing.entity.domains.universe.error.exception.UniverseNotFoundException;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.dto.UniverseInfo;
import com.owing.entity.domains.universe.repository.UniverseDeletedRepository;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.repository.UniverseRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class UniverseAdapter extends BaseFileAdapter<Universe, UniverseFolder> {
	private final UniverseRepository universeRepository;
	private final UniverseDeletedRepository universeDeletedRepository;

	@Override
	protected BaseFileRepository<Universe, UniverseFolder> dndRepository() {
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
}

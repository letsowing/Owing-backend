package com.owing.entity.domains.universe.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.folder.adapter.BaseFolderAdapter;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.repository.UniverseFolderRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class UniverseFolderAdapter extends BaseFolderAdapter<UniverseFolder> {

	private final UniverseFolderRepository universeFolderRepository;

	@Override
	protected BaseDndRepository<UniverseFolder> dndRepository() {
		return universeFolderRepository;
	}
}

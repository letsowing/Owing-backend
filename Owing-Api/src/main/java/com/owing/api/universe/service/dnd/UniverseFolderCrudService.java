package com.owing.api.universe.service.dnd;

import org.springframework.stereotype.Service;

import com.owing.api.dnd.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.service.DndFolderCrudService;
import com.owing.api.universe.model.mapper.UniverseFolderMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.adapter.DndAdapter;
import com.owing.core.dnd.service.DndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseFolderDndService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UniverseFolderCrudService extends DndFolderCrudService<UniverseFolder> {

	private final MemberUtils memberUtils;
	private final UniverseFolderMapper universeFolderMapper;
	private final UniverseFolderAdapter universeFolderAdapter;
	private final UniverseFolderDndService universeFolderDndService;
	private final TrashCanDomainService trashCanDomainService;

	@Override
	protected MemberUtils memberUtils() {
		return memberUtils;
	}

	@Override
	protected DndService<UniverseFolder> dndService() {
		return universeFolderDndService;
	}

	@Override
	protected DndAdapter<UniverseFolder> folderAdapter() {
		return universeFolderAdapter;
	}

	@Override
	protected TrashCanDomainService trashCanDomainService() {
		return trashCanDomainService;
	}
	@Override
	protected BaseFolderMapper<UniverseFolder> folderMapper() {
		return universeFolderMapper;
	}
}

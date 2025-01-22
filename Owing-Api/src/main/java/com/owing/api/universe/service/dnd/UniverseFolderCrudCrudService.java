package com.owing.api.universe.service.dnd;

import org.springframework.stereotype.Service;

import com.owing.api.dnd.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.service.DndFolderCrudService;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
import com.owing.api.universe.model.mapper.UniverseFolderMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseFolderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UniverseFolderCrudCrudService extends DndFolderCrudService<UniverseFolder> {

	private final MemberUtils memberUtils;
	private final UniverseFolderService universeFolderService;
	private final UniverseFolderMapper universeFolderMapper;
	private final TrashCanFolderAdaptor trashCanFolderAdaptor;
	private final ProjectAdapter projectAdapter;
	private final TrashCanFolderMapper trashCanFolderMapper;
	private final UniverseFolderAdapter universeFolderAdapter;

	@Override
	protected MemberUtils memberUtils() {
		return memberUtils;
	}

	@Override
	protected DndService<UniverseFolder> folderService() {
		return universeFolderService;
	}

	@Override
	protected DndAdapter<UniverseFolder> folderAdapter() {
		return universeFolderAdapter;
	}

	@Override
	protected TrashCanFolderAdaptor trashCanFolderAdaptor() { return this.trashCanFolderAdaptor; }

	@Override
	protected ProjectAdapter projectAdapter() { return this.projectAdapter; }

	@Override
	protected TrashCanFolderMapper trashCanFolderMapper() { return this.trashCanFolderMapper; }

	@Override
	protected BaseFolderMapper<UniverseFolder> folderMapper() {
		return universeFolderMapper;
	}
}

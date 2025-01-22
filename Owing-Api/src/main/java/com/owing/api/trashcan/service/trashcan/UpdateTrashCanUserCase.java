package com.owing.api.trashcan.service.trashcan;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.trashcan.adaptor.TrashCanAdaptor;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateTrashCanUserCase {
	private final TrashCanDomainService trashCanDomainService;
	private final TrashCanAdaptor trashCanAdaptor;
	private final CastNodeAdapter castNodeAdapter;
	private final StoryAdapter storyAdapter;
	private final UniverseAdapter universeAdapter;

	public void executeRestore(Long trashId) {
		TrashCan trashCan = trashCanAdaptor.findById(trashId);

		switch (trashCan.getTableName()) {
			case CAST -> castNodeAdapter.restoreById(trashCan.getItemId());
			case STORY -> storyAdapter.restoreById(trashCan.getItemId());
			case UNIVERSE -> universeAdapter.restoreById(trashCan.getItemId());
		}
		trashCanDomainService.deleteTrashCan(trashId);
	}

}

package com.owing.api.universe.model.mapper;

import org.springframework.stereotype.Component;

import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.entity.domains.universe.adaptor.UniverseAdaptor;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.folders.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniverseMapper {

	private final UniverseAdaptor universeAdaptor;

	/** 세계관 생성 시 사용합니다 **/
	public Universe toEntity(AddUniverseRequest addUniverseRequest, UniverseFolder universeFolder) {
		return Universe.builder()
			.name(addUniverseRequest.name())
			.description(addUniverseRequest.description())
			.imageUrl(addUniverseRequest.imageUrl())
			.universeFolder(universeFolder)
			.build();
	}

	/** 세계관 수정 시 사용합니다 **/
	public Universe toEntity(Universe oldUniverse, UpdateUniverseRequest updateUniverseRequest) {
		return Universe.builder()
			.name(updateUniverseRequest.name())
			.description(updateUniverseRequest.description())
			.imageUrl(updateUniverseRequest.imageUrl())
			.universeFolder(oldUniverse.getUniverseFolder())
			.build();
	}

	public UniverseShortInfoResponse toShortInfoResponse(Universe universe) {
		return UniverseShortInfoResponse.builder()
			.id(universe.getId())
			.name(universe.getName())
			.description(universe.getDescription())
			.imageUrl(universe.getImageUrl())
			.build();
	}
}

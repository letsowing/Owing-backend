package com.owing.api.universe.model.mapper;

import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileTitleRequest;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseImageResponse;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class UniverseMapper extends BaseFileMapper<Universe, UniverseFolder> {

	/**
	 * 세계관 DnD 생성시 사용
	 */
	@Override
	public Universe toEntity(AddFileRequest dto, UniverseFolder folder) {
		return Universe.builder()
			.name(dto.name())
			.folder(folder)
			.build();
	}

	/**
	 * 세계관 DnD 수정시 사용
	 */
	@Override
	public Universe toEntity(UpdateFileTitleRequest dto) {
		return  Universe.builder()
			.name(dto.name())
			.build();
	}

	/**
	 * 세계관 정보 응답
	 */
	@Override
	public UniverseShortInfoResponse toInfoResponse(Universe universe) {
		return UniverseShortInfoResponse.builder()
			.id(universe.getId())
			.name(universe.getName())
			.description(universe.getDescription())
			.imageUrl(universe.getImageUrl())
			.build();
	}

	/** 세계관 생성 시 사용합니다 **/
	public Universe toEntity(AddUniverseRequest addUniverseRequest, UniverseFolder universeFolder) {
		return Universe.builder()
			.name(addUniverseRequest.name())
			.description(addUniverseRequest.description())
			.imageUrl(addUniverseRequest.imageUrl())
			.folder(universeFolder)
			.build();
	}

	/** 세계관 수정 시 사용합니다 **/
	public Universe toEntity(Universe oldUniverse, UpdateUniverseRequest updateUniverseRequest) {
		return Universe.builder()
			.name(updateUniverseRequest.name())
			.description(updateUniverseRequest.description())
			.imageUrl(updateUniverseRequest.imageUrl())
			.folder(oldUniverse.getFolder())
			.build();
	}

	public UniverseImageResponse toGenerateImageResponse(String imgUrl) {
		return UniverseImageResponse.builder()
			.imageUrl(imgUrl)
			.build();
	}

}

package com.owing.api.cast.model.mapper;

import com.owing.api.cast.model.dto.response.CastImageResponse;
import com.owing.common.annotation.Mapper;

import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class CastMapper {

	public CastImageResponse toGenerateImageResponse(String imgUrl) {
		return CastImageResponse.builder()
			.imageUrl(imgUrl)
			.build();
	}
}

package com.owing.api.cast.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.cast.model.dto.request.GenerateCastImageRequest;
import com.owing.api.cast.model.dto.response.CastImageResponse;
import com.owing.api.cast.model.mapper.CastMapper;

import com.owing.openfeign.OwingAiClient;
import com.owing.common.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GenerateCastImageUseCase {
	private final CastMapper castMapper;
	private final OwingAiClient owingAiClient;

	@Transactional("jpaTransactionManager")
	public CastImageResponse execute(GenerateCastImageRequest generateCastImageRequest) {

		/* FeignClient 를 이용해 AI 서버로 이미지 생성 요청 */
		CastImageResponse universeImageResponse = owingAiClient.generateCastImage(generateCastImageRequest);
		String imgUrl = universeImageResponse.imageUrl();
		return castMapper.toGenerateImageResponse(imgUrl);
	}
}

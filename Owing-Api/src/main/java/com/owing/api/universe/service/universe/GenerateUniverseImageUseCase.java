package com.owing.api.universe.service.universe;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.openfeign.OwingAiClient;
import com.owing.api.universe.model.dto.request.GenerateUniverseImageRequest;
import com.owing.api.universe.model.dto.response.UniverseImageResponse;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.common.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GenerateUniverseImageUseCase {
	private final UniverseMapper universeMapper;
	private final OwingAiClient owingAiClient;

	@Transactional(transactionManager = "transactionManager")
	public UniverseImageResponse execute(GenerateUniverseImageRequest generateUniverseImageRequest) {

		/* FeignClient 를 이용해 AI 서버로 이미지 생성 요청 */
		UniverseImageResponse universeImageResponse = owingAiClient.generateUniverseImage(generateUniverseImageRequest);
		String imgUrl = universeImageResponse.imageUrl();
		return universeMapper.toGenerateImageResponse(imgUrl);
	}
}

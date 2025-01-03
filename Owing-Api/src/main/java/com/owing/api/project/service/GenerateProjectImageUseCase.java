package com.owing.api.project.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.openfeign.OwingAiClient;
import com.owing.api.project.model.dto.request.GenerateProjectImageRequest;
import com.owing.api.project.model.dto.response.ProjectImageResponse;
import com.owing.api.project.model.mapper.ProjectMapper;
import com.owing.api.universe.model.dto.response.UniverseImageResponse;
import com.owing.common.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GenerateProjectImageUseCase {
	private final ProjectMapper projectMapper;
	private final OwingAiClient owingAiClient;

	@Transactional(transactionManager = "jpaTransactionManager")
	public ProjectImageResponse execute(GenerateProjectImageRequest generateProjectImageRequest) {

		/* FeignClient 를 이용해 AI 서버로 이미지 생성 요청 */
		ProjectImageResponse projectImageResponse = owingAiClient.generateProjectImage(generateProjectImageRequest);
		String imgUrl = projectImageResponse.imageUrl();
		return projectMapper.toGenerateImageResponse(imgUrl);
	}
}

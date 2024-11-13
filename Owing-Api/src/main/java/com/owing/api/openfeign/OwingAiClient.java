package com.owing.api.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.owing.api.cast.model.dto.request.GenerateCastImageRequest;
import com.owing.api.cast.model.dto.response.CastImageResponse;
import com.owing.api.project.model.dto.request.GenerateProjectImageRequest;
import com.owing.api.project.model.dto.response.ProjectImageResponse;
import com.owing.api.universe.model.dto.request.GenerateUniverseImageRequest;
import com.owing.api.universe.model.dto.response.UniverseImageResponse;

@FeignClient(name = "owing-ai-client",
	url = "https://ai.letsowing.com/v1",
	configuration = FeignClientConfig.class)
public interface OwingAiClient {
	/* 세계관 AI 이미지 생성 */
	@PostMapping("/images/universes")
	UniverseImageResponse generateUniverseImage(@RequestBody GenerateUniverseImageRequest generateUniverseImageRequest);

	/* 작품 AI 이미지 생성 */
	@PostMapping("/images/projects")
	ProjectImageResponse generateProjectImage(@RequestBody GenerateProjectImageRequest generateProjectImageRequest);

	/* 인물 AI 이미지 생성 */
	@PostMapping("/images/cast")
	CastImageResponse generateCastImage(@RequestBody GenerateCastImageRequest generateCastImageRequest);
}

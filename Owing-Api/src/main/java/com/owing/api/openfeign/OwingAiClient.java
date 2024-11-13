package com.owing.api.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.owing.api.project.model.dto.request.GenerateProjectImageRequest;
import com.owing.api.universe.model.dto.request.GenerateUniverseImageRequest;
import com.owing.api.universe.model.dto.response.UniverseImageResponse;

@FeignClient(name = "owing-ai-client",
	url = "http://localhost:8081/v1",
	configuration = FeignClientConfig.class)
public interface OwingAiClient {
	/* 세계관 AI 이미지 생성 */
	@PostMapping("/images/universes")
	UniverseImageResponse generateUniverseImage(@RequestBody GenerateUniverseImageRequest generateUniverseImageRequest);

	/* 작품 AI 이미지 생성 */
	@PostMapping("/images/projects")
	UniverseImageResponse generateProjectImage(@RequestBody GenerateProjectImageRequest generateProjectImageRequest);

}

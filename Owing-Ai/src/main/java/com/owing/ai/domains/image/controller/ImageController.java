package com.owing.ai.domains.image.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.ai.domains.image.dto.request.GenerateCastImageRequest;
import com.owing.ai.domains.image.dto.request.GenerateProjectImageRequest;
import com.owing.ai.domains.image.dto.request.GenerateUniverseImageRequest;
import com.owing.ai.domains.image.dto.response.CastImageResponse;
import com.owing.ai.domains.image.dto.response.ProjectImageResponse;
import com.owing.ai.domains.image.dto.response.UniverseImageResponse;
import com.owing.ai.domains.image.service.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/images")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

	/* OpenAI - 세계관 이미지 생성 요청 후 S3 업로드 */
	@PostMapping("/universes")
	public ResponseEntity<UniverseImageResponse> generateUniverseImage(@RequestBody GenerateUniverseImageRequest imageGenerateRequest) {
		return imageService.generateUniverseImage(imageGenerateRequest);
	}

	/* OpenAI - 작품 이미지 생성 요청 후 S3 업로드 */
	@PostMapping("/projects")
	public ResponseEntity<ProjectImageResponse> generateProjectImage(@RequestBody GenerateProjectImageRequest imageGenerateRequest) {
		return imageService.generateProjectImage(imageGenerateRequest);
	}

	/* OpenAI - 인물 이미지 생성 요청 후 S3 업로드 */
	@PostMapping("/cast")
	public ResponseEntity<CastImageResponse> generateProjectImage(@RequestBody GenerateCastImageRequest imageGenerateRequest) {
		return imageService.generateCastImage(imageGenerateRequest);
	}
}

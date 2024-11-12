package com.owing.ai.domains.image.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.ai.domains.image.dto.request.GenerateUniverseImageRequest;
import com.owing.ai.domains.image.dto.response.UniverseImageResponse;
import com.owing.ai.domains.image.service.UniverseImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/images")
@RequiredArgsConstructor
public class ImageController {

	private final UniverseImageService universeImageService;

	/* OpenAI - 세계관 이미지 생성 요청 후 S3 업로드 */
	@PostMapping("/universes")
	public ResponseEntity<UniverseImageResponse> generateUniverseImage(@RequestBody GenerateUniverseImageRequest imageGenerateRequest) {
		return universeImageService.generateUniverseImage(imageGenerateRequest);
	}
}

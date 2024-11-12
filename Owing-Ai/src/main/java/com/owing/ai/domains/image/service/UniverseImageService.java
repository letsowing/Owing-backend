package com.owing.ai.domains.image.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.owing.ai.domains.chat.strategy.PromptGenerator;
import com.owing.ai.domains.image.dto.request.GenerateUniverseImageRequest;
import com.owing.ai.domains.image.dto.response.UniverseImageResponse;
import com.owing.ai.domains.image.strategy.ImageGenerator;
import com.owing.infrastructure.config.s3.S3Uploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UniverseImageService {

	private final ImageGenerator imageGenerator;
	private final PromptGenerator promptGenerator;
	private final S3Uploader s3Uploader;


	/**
	 * OpenAI API 를 이용해 Universe 파일 이미지를 생성하는 메서드
	 * 이미지 생성 후 S3에 업로드
	 *
	 * @param imageGenerateRequest 파일 생성 요청을 담은 DTO
	 * @return 생성된 이미지의 URL 을 ResponseEntity 로 반환
	 */
	// @Transactional
	public ResponseEntity<UniverseImageResponse> generateUniverseImage(GenerateUniverseImageRequest imageGenerateRequest) {

		/* TextGeneration - 프롬프트 생성 */
		String prompt = promptGenerator.generateUniverseImagePrompt(imageGenerateRequest); // todo: 프롬프트 db 저장

		/* ImageGeneration - 이미지 생성 */
		String b64Json = imageGenerator.generateImage(prompt);

		/* S3 업로드 */
		String url = s3Uploader.uploadBase64ImageToS3(b64Json);

		UniverseImageResponse response = UniverseImageResponse.builder()
			.imageUrl(url)
			.build();

		return ResponseEntity.ok(response);
	}

}

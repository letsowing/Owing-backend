package com.owing.ai.domains.image.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.owing.ai.domains.image.promptGenerator.PromptGenerator;
import com.owing.ai.domains.image.dto.request.GenerateCastImageRequest;
import com.owing.ai.domains.image.dto.request.GenerateProjectImageRequest;
import com.owing.ai.domains.image.dto.request.GenerateUniverseImageRequest;
import com.owing.ai.domains.image.dto.response.CastImageResponse;
import com.owing.ai.domains.image.dto.response.ProjectImageResponse;
import com.owing.ai.domains.image.dto.response.UniverseImageResponse;
import com.owing.ai.domains.image.imageGenerator.ImageGenerator;
import com.owing.infrastructure.config.s3.S3Properties;
import com.owing.infrastructure.config.s3.S3Uploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final ImageGenerator imageGenerator;
	private final PromptGenerator promptGenerator;
	private final S3Uploader s3Uploader;
	private final S3Properties s3Properties;


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
		String prompt = promptGenerator.generateUniverseImagePrompt(imageGenerateRequest); // todo: 프롬프트 저장

		/* ImageGeneration - 이미지 생성 */
		String b64Json = imageGenerator.generateImage(prompt);

		/* S3 업로드 */
		String url = s3Uploader.uploadBase64ImageToS3(s3Properties.s3().directory().universe(), b64Json);

		UniverseImageResponse response = UniverseImageResponse.builder()
			.imageUrl(url)
			.build();

		return ResponseEntity.ok(response);
	}

	/**
	 * OpenAI API 를 이용해 작품 표지 이미지를 생성하는 메서드
	 * 이미지 생성 후 S3에 업로드
	 *
	 * @param generateProjectImageRequest 작품 생성 요청을 담은 DTO
	 * @return 생성된 이미지의 URL 을 ResponseEntity 로 반환
	 */
	// @Transactional
	public ResponseEntity<ProjectImageResponse> generateProjectImage(GenerateProjectImageRequest generateProjectImageRequest) {

		/* TextGeneration - 프롬프트 생성 */
		String prompt = promptGenerator.generateProjectImagePrompt(generateProjectImageRequest); // todo: 프롬프트 저장

		/* ImageGeneration - 이미지 생성 */
		String b64Json = imageGenerator.generateImage(prompt);

		/* S3 업로드 */
		String url = s3Uploader.uploadBase64ImageToS3(s3Properties.s3().directory().project(), b64Json);

		ProjectImageResponse response = ProjectImageResponse.builder()
			.imageUrl(url)
			.build();

		return ResponseEntity.ok(response);
	}

	/**
	 * OpenAI API 를 이용해 인물 이미지를 생성하는 메서드
	 * 이미지 생성 후 S3에 업로드
	 *
	 * @param generateCastImageRequest 인물 생성 요청을 담은 DTO
	 * @return 생성된 이미지의 URL 을 ResponseEntity 로 반환
	 */
	// @Transactional
	public ResponseEntity<CastImageResponse> generateCastImage(GenerateCastImageRequest generateCastImageRequest) {

		/* TextGeneration - 프롬프트 생성 */
		String prompt = promptGenerator.generateCastImagePrompt(generateCastImageRequest); // todo: 프롬프트 저장

		/* ImageGeneration - 이미지 생성 */
		String b64Json = imageGenerator.generateImage(prompt);

		/* S3 업로드 */
		String url = s3Uploader.uploadBase64ImageToS3(s3Properties.s3().directory().cast(), b64Json);

		CastImageResponse response = CastImageResponse.builder()
			.imageUrl(url)
			.build();

		return ResponseEntity.ok(response);
	}

}

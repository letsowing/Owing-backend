package com.owing.entity.domains.file.service;

import java.time.Duration;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.file.error.FileErrorCode;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileDomainService {

	private final S3Client s3Client;
	private final S3Presigner s3Presigner;
	@Value("${cloud.s3.bucket}")
	private String bucketName;

	// 파일 업로드용 Presigned URL 반환 (PUT 메서드)
	public String getPresignUrl(String filename) {

		if(filename == null || filename.equals("")) {
			return null;
		}

		// 파일 확장자를 추출하고 contentType 설정
		String[] splittedFileName = filename.split("\\.");
		String extension = splittedFileName[splittedFileName.length - 1].equalsIgnoreCase("jpg")
			? "jpeg" : splittedFileName[splittedFileName.length - 1].toLowerCase();

		// 허용된 이미지 확장자 확인
		String regExp = "^(jpeg|png|gif|bmp)$";
		if (!Pattern.matches(regExp, extension)) {
			throw new IllegalArgumentException("올바르지 않은 이미지 확장자입니다."); // todo: 변경
			// throw FileNotValidException.of(FileErrorCode.FILE_NOT_VALID);
		}

		// contentType 생성
		String contentType = "image/" + extension;

		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
			.bucket(bucketName)
			.key(filename)
			.contentType(contentType) // contentType 추가
			.build();

		PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
			.signatureDuration(Duration.ofMinutes(10)) // presignedURL 10분간 접근 허용
			.putObjectRequest(putObjectRequest)
			.build();

		PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);
		String url = presignedPutObjectRequest.url().toString();

		s3Presigner.close(); // presigner를 닫고 획득한 모든 리소스를 해제
		return url;
	}

}

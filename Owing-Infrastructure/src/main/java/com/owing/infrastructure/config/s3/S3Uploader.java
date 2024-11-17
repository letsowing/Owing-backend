package com.owing.infrastructure.config.s3;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.owing.infrastructure.config.s3.error.S3ErrorCode;
import com.owing.infrastructure.config.s3.error.exception.S3InvalidFileException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Component
@RequiredArgsConstructor
public class S3Uploader {
	public static final int SIGNATURE_DURATION_MIN = 10;
	private final S3Client s3Client;
	private final S3Presigner s3Presigner;
	private final S3Properties s3Properties;

	// 파일 업로드용 Presigned URL 반환 (PUT 메서드)
	public String getPresignUrl(String filename) {

		if(filename == null || filename.equals("")) {
			return null;
		}

		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
			.bucket(s3Properties.s3().bucket())
			.key(filename)
			.contentType(getContentType(filename)) // contentType 추가
			.build();

		PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
			.signatureDuration(Duration.ofMinutes(SIGNATURE_DURATION_MIN)) // presignedURL 10분간 접근 허용
			.putObjectRequest(putObjectRequest)
			.build();

		PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);
		String url = presignedPutObjectRequest.url().toString();

		s3Presigner.close(); // presigner를 닫고 획득한 모든 리소스를 해제
		return url;
	}

	private String getContentType(String fileName) {
		// 파일 확장자를 추출하고 contentType 설정
		String[] splittedFileName = fileName.split("\\.");
		String extension = splittedFileName[splittedFileName.length - 1].equalsIgnoreCase("jpg")
			? "jpeg" : splittedFileName[splittedFileName.length - 1].toLowerCase();

		// 허용된 이미지 확장자 확인
		String regExp = "^(jpeg|png|gif|bmp|svg|webp)$";
		if (!Pattern.matches(regExp, extension)) {
			throw S3InvalidFileException.of(S3ErrorCode.INVALID_EXTENSION);
		}

		return "image/" + extension; // contentType 생성
	}

	/* Base64 이미지를 S3에 업로드  */
	public String uploadBase64ImageToS3(String directory, String b64Json) {

		String fileName = UUID.randomUUID().toString(); // 랜덤 파일 이름
		String now = String.valueOf(System.currentTimeMillis()); //파일 업로드 시간
		String randomFileName = directory + "/" + fileName + "_" + now + ".png";

		// Base64 문자열을 바이트 배열로 디코딩
		byte[] decodedBytes = Base64.getDecoder().decode(b64Json);

		// 바이트 배열을 InputStream으로 변환
		InputStream inputStream = new ByteArrayInputStream(decodedBytes);

		return uploadFileToS3(randomFileName, inputStream, decodedBytes.length);
	}

	private String uploadFileToS3(String fileName, InputStream inputStream, long contentLength) {

		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
			.bucket(s3Properties.s3().bucket())
			.key(fileName)
			.contentType(getContentType(fileName))
			.build();

		s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, contentLength));

		GetUrlRequest getUrlRequest = GetUrlRequest.builder()
			.bucket(s3Properties.s3().bucket())
			.key(fileName)
			.build();

		return s3Client.utilities().getUrl(getUrlRequest).toString();

	}

}


package com.owing.api.file.service;

import com.owing.api.file.dto.response.FileResponse;
import com.owing.api.file.mapper.FileMapper;
import com.owing.api.file.utils.FileUtils;
import com.owing.common.annotation.UseCase;
import com.owing.infrastructure.config.s3.S3Uploader;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreatePresignedUrlUseCase {
	private final S3Uploader s3Uploader;
	private final FileMapper fileMapper;

	public FileResponse execute(String directory, String fileExtension) {

		String randomFileName = FileUtils.buildFileName(fileExtension);
		String presignUrl = s3Uploader.getPresignUrl(directory + "/" + randomFileName);
		// presignUrl 에서 쿼리 파라미터를 제외한 fileUrl 생성
		String fileUrl = presignUrl.contains("?") ? presignUrl.substring(0, presignUrl.indexOf("?")) : "";

		return fileMapper.toFileResponse(presignUrl, fileUrl);
	}
}

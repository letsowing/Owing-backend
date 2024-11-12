package com.owing.api.project.service;

import com.owing.api.file.dto.response.FileResponse;
import com.owing.api.file.service.CreatePresignedUrlUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.infrastructure.config.s3.S3Properties;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateProjectPresignedUrlUseCase {
	private final CreatePresignedUrlUseCase createPresignedUrlUseCase;
	private final S3Properties s3Properties;

	public FileResponse execute(String fileExtension) {
		return createPresignedUrlUseCase.execute(s3Properties.s3().directory().project(), fileExtension);
	}

}

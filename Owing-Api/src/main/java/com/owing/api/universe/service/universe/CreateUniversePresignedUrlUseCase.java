package com.owing.api.universe.service.universe;

import com.owing.api.file.dto.response.FileResponse;
import com.owing.api.file.service.CreatePresignedUrlUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.infrastructure.config.s3.S3Properties;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateUniversePresignedUrlUseCase {
	private final CreatePresignedUrlUseCase createPresignedUrlUseCase;
	private final S3Properties s3Properties;

	public FileResponse execute(String fileExtension) {
		return createPresignedUrlUseCase.execute(s3Properties.s3().directory().universe(), fileExtension);
	}

}

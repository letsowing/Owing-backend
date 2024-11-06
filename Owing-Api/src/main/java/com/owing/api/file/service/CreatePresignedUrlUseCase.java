package com.owing.api.file.service;

import com.owing.api.file.dto.response.FileResponse;
import com.owing.api.file.mapper.FileMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.file.service.FileDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreatePresignedUrlUseCase {
	private final FileDomainService fileDomainService;
	private final FileMapper fileMapper;

	public FileResponse execute(String fileName) {

		String presignUrl = fileDomainService.getPresignUrl(fileName);
		// presignUrl 에서 쿼리 파라미터를 제외한 fileUrl 생성
		String fileUrl = presignUrl.contains("?") ? presignUrl.substring(0, presignUrl.indexOf("?")) : "";

		return fileMapper.toFileResponse(presignUrl, fileUrl);
	}
}

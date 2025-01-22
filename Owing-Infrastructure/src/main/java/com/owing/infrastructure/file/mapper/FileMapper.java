package com.owing.infrastructure.file.mapper;

import com.owing.infrastructure.file.dto.response.FileResponse;
import com.owing.common.annotation.Mapper;

import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class FileMapper {

	public FileResponse toFileResponse(String presignedUrl, String fileUrl) {
		return FileResponse.builder()
			.presignedUrl(presignedUrl)
			.fileUrl(fileUrl)
			.build();
	}
}

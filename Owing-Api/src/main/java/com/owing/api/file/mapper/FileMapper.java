package com.owing.api.file.mapper;

import com.owing.api.file.dto.response.FileResponse;
import com.owing.common.annotation.Mapper;

import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class FileMapper {

	public FileResponse toFileResponse(String presignedUrl, String fileUrl, String fileName) {
		return FileResponse.builder()
			.presignedUrl(presignedUrl)
			.fileUrl(fileUrl)
			.fileName(fileName)
			.build();
	}
}

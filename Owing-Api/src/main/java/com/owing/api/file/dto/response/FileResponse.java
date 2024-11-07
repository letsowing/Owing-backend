package com.owing.api.file.dto.response;

import lombok.Builder;

@Builder
public record FileResponse(
	String presignedUrl,  // 클라이언트가 파일을 업로드하는 임시 경로
	String fileUrl, // 최종적으로 파일이 저장될 위치,
	String fileName // 랜덤 문자열이 추가된 파일 이름
) { }

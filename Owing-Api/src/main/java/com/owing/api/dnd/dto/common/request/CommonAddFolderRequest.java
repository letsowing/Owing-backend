package com.owing.api.dnd.dto.common.request;

import com.owing.api.dnd.dto.request.AddFolderRequest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommonAddFolderRequest(
	@NotNull(message = "파일명은 필수입니다.")
	@Size(min = 1, max = 50, message = "파일명은 1자 이상 50자 이하로 입력해야 합니다.")
	String name,

	@NotNull(message = "프로젝트 ID는 필수입니다.")
	Long projectId
) implements AddFolderRequest {
}

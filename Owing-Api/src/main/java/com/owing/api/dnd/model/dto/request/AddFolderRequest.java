package com.owing.api.dnd.model.dto.request;

public record AddFolderRequest(
		String name,
		Long projectId
) implements AddDndRequest {
	public static AddFolderRequest initialFolder(Long projectId) {
		return new AddFolderRequest("기본 폴더", projectId);
	}
}

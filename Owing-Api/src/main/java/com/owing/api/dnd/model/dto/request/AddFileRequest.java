package com.owing.api.dnd.model.dto.request;

public record AddFileRequest(
		String name,
		Long folderId
) implements AddDndRequest {
}

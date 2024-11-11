package com.owing.api.dnd.file.model.dto.request;

import com.owing.api.dnd.base.model.dto.request.AddDndRequest;

public record AddFileRequest(
		String title,
		Long folderId
) implements AddDndRequest {
}

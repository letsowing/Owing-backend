package com.owing.api.dnd.folder.model.dto.request;

import com.owing.api.dnd.base.model.dto.request.AddDndRequest;

public record AddFolderRequest(
		String name,
		Long projectId
) implements AddDndRequest {
}

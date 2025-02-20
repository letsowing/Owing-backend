package com.owing.api.dnd.dto.common.request;

import com.owing.api.dnd.dto.request.AddFileRequest;

public record CommonAddFileRequest(
		String name,
		Long folderId

)implements AddFileRequest {
}

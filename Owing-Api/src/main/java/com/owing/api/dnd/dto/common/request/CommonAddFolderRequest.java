package com.owing.api.dnd.dto.common.request;

import com.owing.api.dnd.dto.request.AddFolderRequest;

public record CommonAddFolderRequest(
	String name,
	Long projectId
) implements AddFolderRequest {
}

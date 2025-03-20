package com.owing.api.dnd.dto.common.request;

import com.owing.api.dnd.dto.request.UpdateFolderNameRequest;

public record CommonUpdateFolderNameRequest(
	String name
) implements UpdateFolderNameRequest {
}

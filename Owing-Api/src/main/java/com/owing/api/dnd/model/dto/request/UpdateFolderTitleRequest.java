package com.owing.api.dnd.model.dto.request;

public record UpdateFolderTitleRequest(
	String name
) implements UpdateDndRequest {
}

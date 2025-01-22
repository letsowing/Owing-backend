package com.owing.api.dnd.model.dto.request;

public record UpdateFileTitleRequest(
	String name
) implements UpdateDndRequest {
}

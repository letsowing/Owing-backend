package com.owing.api.dnd.file.model.dto.request;

import com.owing.api.dnd.base.model.dto.request.UpdateDndRequest;

public record UpdateFileTitleRequest(
	String name
) implements UpdateDndRequest {
}

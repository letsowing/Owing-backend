package com.owing.api.dnd.folder.model.dto.request;

import com.owing.api.dnd.base.model.dto.request.UpdateDndRequest;

public record UpdateFolderTitleRequest(
	String name
) implements UpdateDndRequest {
}

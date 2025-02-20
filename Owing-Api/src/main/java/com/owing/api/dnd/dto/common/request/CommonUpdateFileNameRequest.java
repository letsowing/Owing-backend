package com.owing.api.dnd.dto.common.request;

import com.owing.api.dnd.dto.request.UpdateFileNameRequest;

public record CommonUpdateFileNameRequest(
	String name
) implements UpdateFileNameRequest {
}

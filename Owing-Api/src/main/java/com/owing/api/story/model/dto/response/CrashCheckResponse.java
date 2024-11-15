package com.owing.api.story.model.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CrashCheckResponse{

	Items[] items;

	@Getter
	public static class Items {
		String base;
		String add;
		String reason;
		LocalDateTime createdAt = LocalDateTime.now();
	}

	public static CrashCheckResponse of(Items[] items) {
		CrashCheckResponse response = new CrashCheckResponse();
		response.items = items;
		return response;
	}
}
package com.owing.api.trashcan.model.dto.response;

import java.time.LocalDateTime;

import com.owing.entity.domains.trashcan.model.TrashCan;

public record TrashCanResponse(
	Long id,
	Long itemId,
	String name,
	String description,
	String imageUrl,
	LocalDateTime createdAt
) {
	public static TrashCanResponse from(TrashCan trashCan) {
		return new TrashCanResponse(
			trashCan.getId(),
			trashCan.getItemId(),
			trashCan.getName(),
			trashCan.getDescription(),
			trashCan.getImageUrl(),
			trashCan.getCreatedAt()
		);
	}
}

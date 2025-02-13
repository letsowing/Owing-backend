package com.owing.api.trashcan.model.dto.response;

import com.owing.entity.domains.trashcan.model.TrashCan;

public record File(Long id, String name, String description, String imageUrl) {
	public static File from(TrashCan trashCan) {
		return new File(
			trashCan.getId(),
			trashCan.getName(),
			trashCan.getDescription(),
			trashCan.getImageUrl()
		);
	}
}

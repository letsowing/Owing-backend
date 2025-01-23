package com.owing.api.cast.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateCastFolderInfo(
        @NotBlank(message = "CastFolder 이름은 필수입니다.")
        String name,
        String description
) {
}

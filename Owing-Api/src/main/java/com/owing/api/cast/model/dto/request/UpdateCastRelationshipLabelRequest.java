package com.owing.api.cast.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateCastRelationshipLabelRequest(
        @NotBlank(message = "관계의 이름은 필수입니다.")
        String label
) {
}

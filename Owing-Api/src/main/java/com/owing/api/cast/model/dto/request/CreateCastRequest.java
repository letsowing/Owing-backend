package com.owing.api.cast.model.dto.request;

import com.owing.node.domains.cast.model.Coordinate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateCastRequest(
        @NotNull(message = "소속되는 folderId는 필수입니다.")
        Long folderId,

        @NotBlank(message = "캐릭터의 이름은 필수입니다.")
        String name,

        @PositiveOrZero(message = "캐릭터의 나이는 음수일 수 없습니다.")
        Long age,

        String gender,
        String role,
        String description,
        String imageUrl,
        Coordinate coordinate
) {
}

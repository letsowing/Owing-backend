package com.owing.api.cast.model.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.owing.node.domains.cast.model.ConnectionHandle;
import com.owing.node.domains.cast.model.ConnectionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateConnectionRequest(
        @NotBlank(message = "관계 라벨은 필수입니다.")
        String label,

        @NotNull(message = "관계 연결 타입은 필수입니다.")
        ConnectionType type,

        @NotNull(message = "관계의 시작 노드는 필수입니다.")
        @JsonAlias("source")
        Long sourceId,

        @NotNull(message = "시작 노드의 관계 시작 위치는 필수입니다.")
        ConnectionHandle sourceHandle,

        @NotNull(message = "관계의 대상 노드는 필수입니다.")
        @JsonAlias("target")
        Long targetId,

        @NotNull(message = "대상 노드의 관계 시작 위치는 필수입니다.")
        ConnectionHandle targetHandle
) {
}

package com.owing.entity.domains.ai.log.story.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record CrashCheckOutput(
        String base,
        String add,
        String reason
) {
}

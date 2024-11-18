package com.owing.entity.domains.ai.log.story.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record SpellCheckOutput(
        String help,
        Integer errorIdx,
        Integer correctMethod,
        Integer start,
        String errMsg,
        Integer end,
        String orgStr,
        String candWord
) {
}

package com.owing.ai.domains.story.ai.v3.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SummaryResponse(
	@JsonProperty(required = true, value = "items") Items[] items
) {

	public record Items(
		@JsonProperty(required = true, value = "title") String title,
		@JsonProperty(required = true, value = "analysis") String analysis
	) {
		@Override
		public String toString() {
			return title + " : " + analysis;
		}
	}
}

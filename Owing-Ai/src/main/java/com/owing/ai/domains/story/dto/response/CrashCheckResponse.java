package com.owing.ai.domains.story.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CrashCheckResponse(
	@JsonProperty(required = true, value = "reply") Reply replies) {

	record Reply(
		@JsonProperty(required = true, value = "items") Items[] items) {

		record Items(
			@JsonProperty(required = true, value = "base") String base,
			@JsonProperty(required = true, value = "add") String add,
			@JsonProperty(required = true, value = "reason") String reason
		) {
		}
	}
}

// String jsonSchema = """
// 	{
// 		"type": "object",
// 		"properties": {
// 			"reply": {
// 				"type": "array",
// 				"items": {
// 					"type": "object",
// 					"properties": {
// 						"explain": { "type": "string" }
// 					},
// 					"required": ["explain"],
// 					"additionalProperties": false
// 				}
// 			},
// 		},
// 		"additionalProperties": false
// 	}
// 	""";
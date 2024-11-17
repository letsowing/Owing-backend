package com.owing.ai.domains.story.dto.response.spellCheck;

import org.jsoup.parser.Parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorInfo(
	String help,
	Integer errorIdx,
	Integer correctMethod,
	Integer start,
	String errMsg,
	Integer end,
	String orgStr,
	String candWord
) {
	// 정적 팩토리 메서드
	@JsonCreator
	public static ErrorInfo create(
		@JsonProperty("help") String help,
		@JsonProperty("errorIdx") Integer errorIdx,
		@JsonProperty("correctMethod") Integer correctMethod,
		@JsonProperty("start") Integer start,
		@JsonProperty("errMsg") String errMsg,
		@JsonProperty("end") Integer end,
		@JsonProperty("orgStr") String orgStr,
		@JsonProperty("candWord") String candWord
	) {
		String processedHelp = processHelpText(help);
		return new ErrorInfo(processedHelp, errorIdx, correctMethod, start, errMsg, end, orgStr, candWord);
	}

	private static String processHelpText(String helpText) {
		String decodedText = Parser.unescapeEntities(helpText, true);
		return decodedText.replace("<br/>", "\n");
	}
}
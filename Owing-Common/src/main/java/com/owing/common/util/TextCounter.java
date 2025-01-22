package com.owing.common.util;

import org.jsoup.Jsoup;

public class TextCounter {
	public static int countText(String content, boolean whiteSpace, boolean lineBreak){
		String text = Jsoup.parse(content).text();

		if (whiteSpace) {
            text = text.replaceAll("\\s+", "");
        }

		if (lineBreak) {
            text = text.replaceAll("\\n+", "");
        }

		return text.length();
	}
}

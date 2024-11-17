package com.owing.ai.domains.story.ai.v2.util;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Component;

@Component
public class StoryAITokenTextSplitter {

	public List<Document> splitDocuments(List<Document> documents) {
		TokenTextSplitter splitter = new TokenTextSplitter();
		return splitter.apply(documents);
	}

	public List<Document> splitCustomized(List<Document> documents) {
		TokenTextSplitter splitter = new TokenTextSplitter(1000, 400, 10, 5000, true);
		return splitter.apply(documents);
	}
}

package com.owing.ai.domains.story.ai;

import org.springframework.ai.chat.prompt.Prompt;

import com.owing.ai.domains.story.dto.request.crashCheck.CrashCheckRequest;

public interface AnalyzePromptGenerator {
	Prompt generatePrompt(CrashCheckRequest request);
}

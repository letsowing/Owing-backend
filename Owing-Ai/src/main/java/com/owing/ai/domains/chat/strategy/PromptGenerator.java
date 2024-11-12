package com.owing.ai.domains.chat.strategy;

import com.owing.ai.domains.image.dto.request.GenerateUniverseImageRequest;

public interface PromptGenerator {
	String generateUniverseImagePrompt(GenerateUniverseImageRequest universeImageRequest);
}

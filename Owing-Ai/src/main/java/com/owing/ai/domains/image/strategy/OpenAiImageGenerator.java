package com.owing.ai.domains.image.strategy;

import org.springframework.ai.image.ImageMessage;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Component;

import com.owing.ai.global.properties.OpenAiProperties;

import lombok.RequiredArgsConstructor;

@Component("openAiImageGenerator")
@RequiredArgsConstructor
public class OpenAiImageGenerator implements ImageGenerator {

	private final ImageModel imageModel;
	private final OpenAiProperties openAiProperties;


	@Override
	public String generateImage(String prompt) {
		ImageMessage imageMessage = new ImageMessage(prompt, 1.0f);
		OpenAiImageOptions imageOptions = imageOptions();

		ImagePrompt imagePrompt = new ImagePrompt(imageMessage, imageOptions);
		ImageResponse response = imageModel.call(imagePrompt);

		if (response.getResults() != null && !response.getResults().isEmpty()) {
			return response.getResult().getOutput().getB64Json();
			// return response.getResult().getOutput().toString();
		} else {
			throw new RuntimeException();
		}
	}

	private OpenAiImageOptions imageOptions() {
		String size = openAiProperties.image().options().size();
		if (!size.matches("1024x1024|1792×1024|1024×1792")) {
			throw new IllegalArgumentException("Invalid size for DALL-E 3 model. Allowed sizes: 1024x1024, 1792×1024, 1024×1792");
		}

		String[] sizeParts = size.split("x");
		int width = Integer.parseInt(sizeParts[0]);
		int height = Integer.parseInt(sizeParts[1]);

		return OpenAiImageOptions.builder()
			.withModel(openAiProperties.image().options().model().getValue())
			.withResponseFormat(openAiProperties.image().options().responseFormat())
			.withQuality(openAiProperties.image().options().quality())
			.withN(1)
			.withHeight(height)
			.withWidth(width)
			.build();
	}
}

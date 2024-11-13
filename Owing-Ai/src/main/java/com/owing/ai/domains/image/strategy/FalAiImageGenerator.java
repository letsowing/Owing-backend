package com.owing.ai.domains.image.strategy;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.owing.ai.global.properties.FalAiProperties;

import ai.fal.client.*;
import ai.fal.client.queue.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component("falAiImageGenerator")
@RequiredArgsConstructor
public class FalAiImageGenerator implements ImageGenerator {

	private final FalAiProperties falAiProperties;
	private FalClient falClient; // FalClient 인스턴스 선언

	// API 키 설정 및 FalClient 초기화
	@PostConstruct
	public void init() {
		this.falClient = FalClient.withConfig(
			ClientConfig.withCredentials(CredentialsResolver.fromApiKey(falAiProperties.apiKey()))
		);
	}
	public String generateImage(String prompt) {

		var input = Map.of(
			"prompt", prompt,
			"seed", 6252023,
			"image_size", "landscape_4_3",
			"num_images", 4
		);

		var result = falClient.subscribe(falAiProperties.model(),
			SubscribeOptions.<JsonObject>builder()
				.input(input)
				// .logs(true)
				.resultType(JsonObject.class)
				.onQueueUpdate(update -> {
					if (update instanceof QueueStatus.InProgress) {
						System.out.println(((QueueStatus.InProgress)update).getLogs());
					}
				})
				.build()
		);

		return result.getData().toString();
	}

}

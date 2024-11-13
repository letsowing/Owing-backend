package com.owing.ai.domains.image.imageGenerator;

import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.owing.ai.global.properties.FalAiProperties;

import ai.fal.client.ClientConfig;
import ai.fal.client.CredentialsResolver;
import ai.fal.client.FalClient;
import ai.fal.client.SubscribeOptions;
import ai.fal.client.queue.QueueStatus;
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
			"num_images", 1
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

		String url = "";
		JsonArray imagesArray = result.getData().getAsJsonArray("images");
		if (imagesArray != null && imagesArray.size() > 0) {
			JsonObject firstImageObject = imagesArray.get(0).getAsJsonObject();
			url = firstImageObject.get("url").getAsString();
		} else {
			System.out.println("No images found.");
		}

		return urlToBase64(url);
	}

	/* URL에 담긴 이미지를 Base64 형식으로 변환하는 함수 */
	private String urlToBase64(String imageUrl) {
		try {
			// URL에서 InputStream을 얻음
			URL url = new URL(imageUrl);
			InputStream inputStream = url.openStream();

			// InputStream의 모든 바이트를 읽음
			byte[] imageBytes = inputStream.readAllBytes();
			inputStream.close();

			// 바이트 배열을 Base64로 인코딩
			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

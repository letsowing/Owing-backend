package com.owing.ai.domains.story.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.ai.domains.story.dto.request.spellCheck.SpellCheckRequest;
import com.owing.ai.domains.story.dto.response.spellCheck.ErrorInfo;
import com.owing.ai.domains.story.error.StoryAIErrorCode;
import com.owing.ai.domains.story.error.exception.StoryAIException;

@Service
public class SpellCheckerService {
	private final ObjectMapper objectMapper;
	private final HttpClient httpClient;
	private static final String PNU_URL = "http://speller.cs.pusan.ac.kr/results";

	public SpellCheckerService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		this.httpClient = HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(10))
			.build();
	}

	public List<ErrorInfo> checkSpelling(SpellCheckRequest request) {
		String parsedContent = Jsoup.parse(request.content()).text();

		if(parsedContent.isBlank()){
			return null;
		}

		String response = sendPostRequest(parsedContent);
		String parsedJsonString = parseStringJsonResult(response);

		if(parsedJsonString == null || parsedJsonString.isBlank()){
			return null;
		}

		System.out.println(parsedJsonString);
		return convertToDto(parsedJsonString);
	}

	public String sendPostRequest(String sentence) { // 일단 나중에 고치겠어요...
		try {
			String params = "text1=" + URLEncoder.encode(sentence, StandardCharsets.UTF_8);

			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(PNU_URL))
				.timeout(Duration.ofSeconds(10))
				.header("Content-Type", "application/x-www-form-urlencoded")
				.POST(HttpRequest.BodyPublishers.ofString(params))
				.build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() != 200) {
				throw StoryAIException.of(StoryAIErrorCode.SPELL_CHECK_FAILED, "맞춤법 검사 중 에러 발생: " + response.statusCode());
			}
			return response.body();
		} catch (Exception e) {
			throw StoryAIException.of(StoryAIErrorCode.SPELL_CHECK_FAILED, "맞춤법 검사 중 에러 발생: " + e.getMessage());
		}
	}

	private String parseStringJsonResult(String response) {
		Document doc = Jsoup.parse(response);

		Elements dataElements = doc.select("script:containsData(data = [)");
		if (dataElements.isEmpty()) {
			System.out.println("No spell check data found.");
			return null;
		}

		return dataElements.html().replaceFirst("(?s).*data = (\\[.*?\\]);.*", "$1");
	}

	private List<ErrorInfo> convertToDto(String jsonData) {
		try {
			JsonNode jsonArray = objectMapper.readTree(jsonData);
			JsonNode errInfo = jsonArray.get(0).get("errInfo");
			return objectMapper.convertValue(errInfo, new TypeReference<>() {});

		} catch (Exception e) {
			throw StoryAIException.of(StoryAIErrorCode.SPELL_CHECK_FAILED, e.getMessage());
		}
	}
}


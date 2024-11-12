package com.owing.ai.domains.chat.strategy;

import java.util.List;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionRequest.ResponseFormat;
import org.springframework.stereotype.Component;

import com.owing.ai.domains.image.dto.request.GenerateUniverseImageRequest;
import com.owing.ai.global.properties.OpenAiProperties;

import lombok.RequiredArgsConstructor;

@Component("openAiPromptGenerator")
@RequiredArgsConstructor
public class OpenAiPromptGenerator implements PromptGenerator {

	private final ChatModel chatModel;
	private final OpenAiProperties openAiProperties;

	/**
	 * OpenAI API 를 이용해 세계관 이미지를 위한 프롬프트를 생성하는 메서드
	 *
	 * @param universeImageRequest 작품 정보가 담긴 DTO
	 * @return 세계관 이미지 생성 시 요청할 프롬프트
	 */
	@Override
	public String generateUniverseImagePrompt(GenerateUniverseImageRequest universeImageRequest) {

		String systemContent = "[상황]\n"
			+ "- 너는 글을 쓰는 작가를 돕는 훌륭한 프롬프트 엔지니어야. \n"
			+ "- 작가가 그리는 세계관의 이미지를 생성할 수 있도록, 주어진 정보를 바탕으로 구체적인 프롬프트를 작성할거야.\n"
			+ "- 아래 [입력값]을 꼼꼼히 읽고, [지시사항]에 따라 프롬프트를 작성해줘.\n"
			+ "\n"
			+ "[지시사항]\n"
			+ "- name과 description을 바탕으로 [주어 + 행위 + 배경 + 스타일 + 사진]의 비율 정도를 말해주고, 필요하다면 더 디테일하게 [각도 / 포즈 / 구도 / 색상 / 시대 / 계절] 등 다양한 요소들에 대해 언급할 것.\n"
			+ "- 영어로 작성할 것."
			+ "- 모르는 정보에 대해 너의 의견을 개입하지 말 것.";

		String userContent = String.format(
			"[입력값]\n"
				+ "- 세계관의 이름 (name) : [%s]\n"
				+ "- 세계관에 대한 부가 설명 (description) : [%s]\n"
			, universeImageRequest.name()
			, universeImageRequest.description()
		);

		SystemMessage systemMessage = new SystemMessage(systemContent);
		UserMessage userMessage = new UserMessage(userContent);
		Prompt prompt = new Prompt(List.of(systemMessage, userMessage), chatOptions());

		ChatResponse chatResponse = chatModel.call(prompt);
		String result = chatResponse.getResult().getOutput().getContent();

		return result;
	}

	private OpenAiChatOptions chatOptions() {
		return OpenAiChatOptions.builder()
			.withModel(openAiProperties.chat().options().model())
			.withTemperature(Double.parseDouble(openAiProperties.chat().options().temperature()))
			.withMaxTokens(Integer.parseInt(openAiProperties.chat().options().maxTokens()))
			.withResponseFormat(new ResponseFormat(ResponseFormat.Type.TEXT))
			.build();
	}
}

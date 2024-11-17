package com.owing.ai.domains.image.promptGenerator;

import java.util.Arrays;
import java.util.List;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionRequest.ResponseFormat;
import org.springframework.stereotype.Component;

import com.owing.ai.domains.image.dto.request.GenerateCastImageRequest;
import com.owing.ai.domains.image.dto.request.GenerateProjectImageRequest;
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
	 * @param universeImageRequest 세계관 정보가 담긴 DTO
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
			+ "- name 과 description 을 바탕으로 [주어 + 행위 + 배경 + 스타일 + 사진]의 비율 정도를 말해주고, 필요하다면 더 디테일하게 [각도 / 포즈 / 구도 / 색상 / 시대 / 계절] 등 다양한 요소들에 대해 언급할 것.\n"
			+ "- 이미지에 텍스트는 추가하지 말 것.\n"
			+ "- 프롬프트는 반드시 영어로 작성할 것.\n"
			+ "- 주어진 정보 외에는 어떤 창의적 개입도 하지 말고, 오로지 입력된 정보만을 바탕으로 작성할 것.";

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

	/**
	 * OpenAI API 를 이용해 작품 표지 이미지를 위한 프롬프트를 생성하는 메서드
	 *
	 * @param generateProjectImageRequest 작품 정보가 담긴 DTO
	 * @return 작품 표지 이미지 생성 시 요청할 프롬프트
	 */
	@Override
	public String generateProjectImagePrompt(GenerateProjectImageRequest generateProjectImageRequest) {

		String systemContent = "[상황]\n"
			+ "- 너는 글을 쓰는 작가를 돕는 훌륭한 프롬프트 엔지니어야. \n"
			+ "- 작가가 그리는 작품의 표지 이미지를 생성할 수 있도록, 주어진 정보를 바탕으로 구체적인 프롬프트를 작성할거야.\n"
			+ "- 아래 [입력값]을 꼼꼼히 읽고, [지시사항]에 따라 프롬프트를 작성해줘.\n"
			+ "\n"
			+ "[지시사항]\n"
			+ "- title, description, category, genre 를 바탕으로 작품의 표지 이미지가 될 수 있도록 [주요 요소 + 표현 방식 + 배경 + 스타일 + 사진 구도] 비율로 프롬프트를 구성할 것.\n"
			+ "- 필요시 [각도 / 인물 포즈 / 구도 / 색상 / 시대적 배경 / 계절 등]의 세부 요소를 추가하여 더욱 구체적으로 표현할 것.\n"
			+ "- 이미지에 텍스트는 추가하지 말 것.\n"
			+ "- 프롬프트는 반드시 영어로 작성할 것.\n"
			+ "- 주어진 정보 외에는 어떤 창의적 개입도 하지 말고, 오로지 입력된 정보만을 바탕으로 작성할 것.";

		String userContent = String.format(
			"[입력값]\n"
				+ "- 작품의 이름 (title) : [%s]\n"
				+ "- 작품에 대한 부가 설명 (description) : [%s]\n"
				+ "- 작품 분류 (category) : [%s]\n"
				+ "- 작품 장르 (genre) : [%s]\n"
			, generateProjectImageRequest.title()
			, generateProjectImageRequest.description()
			, generateProjectImageRequest.category()
			, Arrays.toString(generateProjectImageRequest.genres())
		);

		SystemMessage systemMessage = new SystemMessage(systemContent);
		UserMessage userMessage = new UserMessage(userContent);
		Prompt prompt = new Prompt(List.of(systemMessage, userMessage), chatOptions());

		ChatResponse chatResponse = chatModel.call(prompt);
		String result = chatResponse.getResult().getOutput().getContent();

		return result;
	}

	/**
	 * OpenAI API 를 이용해 인물 이미지를 위한 프롬프트를 생성하는 메서드
	 *
	 * @param generateCastImageRequest 인물 정보가 담긴 DTO
	 * @return 인물 이미지 생성 시 요청할 프롬프트
	 */
	@Override
	public String generateCastImagePrompt(GenerateCastImageRequest generateCastImageRequest) {

		String systemContent = "[상황]\n"
			+ "- 너는 글을 쓰는 작가를 돕는 훌륭한 프롬프트 엔지니어야. \n"
			+ "- 작가가 구상하는 인물의 이미지를 실제로 생성할 수 있도록, 주어진 정보를 바탕으로 상세하고 직관적인 프롬프트를 작성할거야.\n"
			+ "- 아래 [입력값]을 꼼꼼히 읽고, [지시사항]에 따라 이미지 생성에 필요한 모든 주요 요소를 포함해 프롬프트를 작성해줘.\n"
			+ "\n"
			+ "[지시사항]\n"
			+ "- title, age, gender, role, description 을 바탕으로 인물의 이미지가 될 수 있도록 [주요 요소 + 표현 방식 + 배경 + 스타일 + 사진 구도] 비율로 프롬프트를 구성할 것.\n"
			+ "- 주요 요소에는 인물의 외모, 표정, 의상, 배경, 계절적 특징, 그리고 인물이 속한 시대적 요소 등을 반영해 구체적으로 묘사할 것.\n"
			+ "- 표현 방식에서는 묘사 수준을 예시로 [극사실적, 회화적, 애니메이션 스타일 등]을 고려하여 명시할 것.\n"
			+ "- 배경은 인물의 역할과 부가 설명에 맞게 적절히 상상해 설정하고, 작품의 분위기를 잘 나타낼 수 있도록 서술할 것.\n"
			+ "- 사진 구도는 [인물의 각도, 포즈, 거리] 등을 조절하여 인물이 돋보일 수 있는 방향으로 구체적으로 지시할 것.\n"
			+ "- 필요에 따라 [조명, 그림자 효과, 색감] 등 세부적 요소를 추가하여 표현할 것.\n"
			+ "- 이미지에 텍스트는 추가하지 말 것.\n"
			+ "- 프롬프트는 반드시 영어로 작성할 것.\n"
			+ "- 주어진 정보 외에는 어떤 창의적 개입도 하지 말고, 오로지 입력된 정보만을 바탕으로 작성할 것.";

		String userContent = String.format(
			"[입력값]\n"
				+ "- 인물의 이름 (name) : [%s]\n"
				+ "- 인물의 나이 (age) : [%s]\n"
				+ "- 인물의 성별 (gender) : [%s]\n"
				+ "- 인물의 역할 (role) : [%s]\n"
				+ "- 작품에 대한 부가 설명 (description) : [%s]\n"
			, generateCastImageRequest.name()
			, generateCastImageRequest.age()
			, generateCastImageRequest.gender()
			, generateCastImageRequest.role()
			, generateCastImageRequest.description()
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

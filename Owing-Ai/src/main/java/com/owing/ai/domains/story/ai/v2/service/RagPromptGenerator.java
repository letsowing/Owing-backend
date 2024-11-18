package com.owing.ai.domains.story.ai.v2.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import com.owing.ai.domains.story.ai.AnalyzePromptGenerator;
import com.owing.ai.domains.story.ai.v1.data.SystemTextV1;
import com.owing.ai.domains.story.ai.v2.model.entity.VectorStoreEntity;
import com.owing.ai.domains.story.dto.request.crashCheck.CrashCheckRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RagPromptGenerator implements AnalyzePromptGenerator {
	private final VectorService vectorService;

	@Override
	public Prompt generatePrompt(CrashCheckRequest request) {
		// 기존 내용을 벡터DB에 업데이트하기
		vectorService.addAllDocuments(request);

		// 벡터DB에서 검색하기
		// List<Document> similar = vectorService.getSimilarDocuments(request.project().id(), "project", request.thisEpisode().content(), 5);
		List<VectorStoreEntity> similar = vectorService.getSimilarDocuments(request.project().id(), request.thisEpisode().id());


		// 현재 내용을 request로 넣기
		String inlined = similar.stream().map(VectorStoreEntity::getContent).collect(Collectors.joining(System.lineSeparator()));
		System.out.println(inlined);

		Message similarDocsMessage = new SystemPromptTemplate(SystemTextV1.v1)
			.createMessage(Map.of("similarDocsMessage", inlined));
		UserMessage userMessage = new UserMessage(request.toString());

		return new Prompt(List.of(similarDocsMessage, userMessage));
	}


}

package com.owing.ai.domains.story.ai.v3.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import com.owing.ai.domains.story.ai.v2.model.entity.StoryInfoType;
import com.owing.ai.domains.story.ai.v2.model.repository.VectorStoreRepository;
import com.owing.ai.domains.story.ai.v2.service.DocumentConvertService;
import com.owing.ai.domains.story.ai.v2.util.StoryAITokenTextSplitter;
import com.owing.ai.domains.story.ai.v3.data.SummaryRequestTextV3;
import com.owing.ai.domains.story.ai.v3.data.SummaryResponse;
import com.owing.ai.domains.story.dto.request.crashCheck.CrashCheckRequest;
import com.owing.ai.domains.story.dto.request.crashCheck.StoryInfoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentConvertServiceV3 implements DocumentConvertService {
	private final VectorStoreRepository vectorStoreRepository;

	private final ChatClient openAiClient;
	private final StoryAITokenTextSplitter splitter;

	protected List<SummaryResponse> summary(String content, StoryInfoType type){
		List<Document> docs = new ArrayList<>(){{
			add(new Document(content));
		}};
		List<Document> splits = splitter.splitCustomized(docs);
		List<SummaryResponse> res = new ArrayList<>();

		// 진짜 이러고 싶지 않은 코드
		for(Document doc: splits) {
			Message system = new SystemMessage(SummaryRequestTextV3.getTemplate(type));
			UserMessage userMessage = new UserMessage("\n\n\n 요약할 데이터의 내용은 다음과 같습니다: \n\n\n" + doc.getContent());

			Prompt prompt = new Prompt(List.of(system, userMessage));

			res.add(openAiClient
				.prompt(prompt)
				.call()
				.entity(SummaryResponse.class));
		}
		return res;
	}

	@Override
	public List<Document> convertToDocument(LocalDateTime last, List<? extends StoryInfoDto> data, StoryInfoType type, Long projectId){
		List<Document> result = new ArrayList<>();
		List<String> ids = new ArrayList<>();

		for(StoryInfoDto dto : data){
			// 업데이트 된 내용이면 모으고
			// id 리스트 모으고
			if(dto.isNewContent(last)){
				// 요약하고
				List<SummaryResponse> res = summary(dto.toString(), type);
				for(SummaryResponse r : res){
					for(SummaryResponse.Items item: r.items())
						result.add(new Document(item.toString(), getMetadataMap(projectId, dto.updatedAt(), dto.id(), type)));
				}
				ids.add(dto.id().toString());
			}
		}
		// 기존 내용을 지워야함
		deleteExistingContent(projectId, type, ids);
        // return data.stream().filter(r -> r.isNewContent(last)).map(r -> preprocess(r, type, projectId)).toList();
		return result;
    }

	// 기존 내용 삭제
	private void deleteExistingContent(Long projectId, StoryInfoType type, List<String> ids) {
		vectorStoreRepository.deleteByTypeAndIdIn(projectId.toString(), type.toString(), ids);
	}


	public List<Document> filterNewContent(CrashCheckRequest request, LocalDateTime last, Long projectId){
		List<Document> result = new ArrayList<>();

		result.addAll(convertToDocument(last, request.prevStory(), StoryInfoType.STORY, projectId));
		result.addAll(convertToDocument(last, request.universe(), StoryInfoType.UNIVERSE, projectId));
		result.addAll(convertToDocument(last, request.cast().castList(), StoryInfoType.CAST, projectId));
		result.addAll(convertToDocument(last, request.cast().relationList(), StoryInfoType.RELATIONSHIP, projectId));
		result.addAll(convertToDocument(last, List.of(request.project()), StoryInfoType.PROJECT, projectId));
		result.addAll(convertToDocument(last, List.of(request.thisEpisode()), StoryInfoType.STORY, projectId));

		return result;
	}

}

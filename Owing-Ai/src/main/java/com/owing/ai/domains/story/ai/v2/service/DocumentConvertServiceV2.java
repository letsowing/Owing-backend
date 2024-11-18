package com.owing.ai.domains.story.ai.v2.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import com.owing.ai.domains.story.ai.v2.model.entity.StoryInfoType;
import com.owing.ai.domains.story.ai.v2.model.repository.VectorStoreRepository;
import com.owing.ai.domains.story.dto.request.crashCheck.CrashCheckRequest;
import com.owing.ai.domains.story.dto.request.crashCheck.StoryInfoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentConvertServiceV2 implements DocumentConvertService {
	private final VectorStoreRepository vectorStoreRepository;

	public List<Document> convertToDocument(LocalDateTime last, List<? extends StoryInfoDto> data, StoryInfoType type, Long projectId){
		List<Document> result = new ArrayList<>();
		List<String> ids = new ArrayList<>();

		for(StoryInfoDto dto : data){
			// 업데이트 된 내용이면 모으고
			// id 리스트 모으고
			if(dto.isNewContent(last)){
				result.add(new Document(dto.toString(), getMetadataMap(projectId, dto.updatedAt(), dto.id(), type)));
				ids.add(dto.id().toString());
			}
		}
		// 기존 내용을 지워야함
		deleteExistingContent(projectId, type, ids);
        // return data.stream().filter(r -> r.isNewContent(last)).map(r -> preprocess(r, type, projectId)).toList();
		return result;
    }

	public Document convertToDocument(LocalDateTime last, StoryInfoDto dto, StoryInfoType type, Long projectId){
		if(dto.isNewContent(last)){
			deleteExistingContent(projectId, type, List.of(dto.id().toString()));
			return new Document(dto.toString(), getMetadataMap(projectId, dto.updatedAt(), dto.id(), type));
		}
		return null;
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
		Optional.ofNullable(convertToDocument(last, request.project(), StoryInfoType.PROJECT, projectId)).ifPresent(result::add);
		Optional.ofNullable(convertToDocument(last, request.thisEpisode(), StoryInfoType.STORY, projectId)).ifPresent(result::add);

		return result;
	}


}

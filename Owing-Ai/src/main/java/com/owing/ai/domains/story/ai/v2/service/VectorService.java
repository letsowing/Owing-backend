package com.owing.ai.domains.story.ai.v2.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.owing.ai.domains.story.ai.v2.model.entity.VectorStoreEntity;
import com.owing.ai.domains.story.ai.v2.model.entity.VectorStoreMetadata;
import com.owing.ai.domains.story.ai.v2.model.repository.VectorStoreMetadataRepository;
import com.owing.ai.domains.story.ai.v2.model.repository.VectorStoreRepository;
import com.owing.ai.domains.story.ai.v2.util.DocumentConverter;
import com.owing.ai.domains.story.ai.v2.util.StoryAITokenTextSplitter;
import com.owing.ai.domains.story.dto.request.crashCheck.CrashCheckRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VectorService {

	private final VectorStore vectorStore;
	private final VectorStoreRepository vectorStoreRepository;
	private final VectorStoreMetadataRepository vectorStoreMetadataRepository;

	private final StoryAITokenTextSplitter splitter;
	private final DocumentConverter converter;

	private static final double SIMILARITY_THRESHOLD = 0.5;
	private static final int TOP_K = 30;

	private VectorStoreMetadata getVectorStoreMetadata(Long projectId) {
		return vectorStoreMetadataRepository.findByProjectId(projectId)
			.orElseGet(() -> VectorStoreMetadata
				.builder()
				.projectId(projectId)
				.updatedAt(LocalDateTime.of(1900, 1, 1, 0, 0))
				.build());
	}

	// 새로 업데이트 된 내용들만 넣기
	public void addAllDocuments(CrashCheckRequest request) {
		Long projectId = request.project().id();
		VectorStoreMetadata metadata = getVectorStoreMetadata(projectId);

		// 새로운 내용들만
		List<Document> filtered = converter.filterNewContent(request, metadata.getUpdatedAt(), projectId);
		metadata.updateUpdatedAt();

		// 기존 내용 삭제
		deleteExistingContent(projectId, filtered);

		// 새로운 내용 넣기
		List<Document> splitDocuments = splitter.splitCustomized(filtered);
		vectorStore.add(splitDocuments);

		// 껍데기 업데이트
		vectorStoreMetadataRepository.save(metadata);
	}

	// 검색용 에반데 쿼리
	public List<VectorStoreEntity> getSimilarDocuments(Long projectId, Long storyId) {
		return vectorStoreRepository.similaritySearch(
			projectId.toString(), storyId.toString(), SIMILARITY_THRESHOLD, TOP_K);
	}

	// 기존 내용 삭제
	private void deleteExistingContent(Long projectId, List<Document> documents) {
		Map<String, List<String>> result = converter.getDeleteIds(documents);
		result.forEach((key, value) -> vectorStoreRepository.deleteByTypeAndIdIn(projectId.toString(), key, value));
	}

}
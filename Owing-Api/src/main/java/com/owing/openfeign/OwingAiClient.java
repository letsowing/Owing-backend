package com.owing.openfeign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.owing.api.cast.model.dto.request.GenerateCastImageRequest;
import com.owing.api.cast.model.dto.response.CastImageResponse;
import com.owing.api.project.model.dto.request.GenerateProjectImageRequest;
import com.owing.api.story.model.dto.request.ai.crashCheck.StoryCrashCheckRequest;
import com.owing.api.story.model.dto.request.StorySpellCheckRequest;
import com.owing.api.story.model.dto.response.CrashCheckResponse;
import com.owing.api.story.model.dto.response.StorySpellCheckResponse;
import com.owing.api.project.model.dto.response.ProjectImageResponse;
import com.owing.api.universe.model.dto.request.GenerateUniverseImageRequest;
import com.owing.api.universe.model.dto.response.UniverseImageResponse;

@FeignClient(name = "owing-ai-client",
	url = "https://ai.letsowing.com/v1",
	// url = "http://localhost:8081/v1",
	configuration = FeignClientConfig.class)
public interface OwingAiClient {
	/* 세계관 AI 이미지 생성 */
	@PostMapping("/images/universes")
	UniverseImageResponse generateUniverseImage(@RequestBody GenerateUniverseImageRequest generateUniverseImageRequest);

	/* 작품 AI 이미지 생성 */
	@PostMapping("/images/projects")
	ProjectImageResponse generateProjectImage(@RequestBody GenerateProjectImageRequest generateProjectImageRequest);

	/* 인물 AI 이미지 생성 */
	@PostMapping("/images/cast")
	CastImageResponse generateCastImage(@RequestBody GenerateCastImageRequest generateCastImageRequest);

	/* 설정 충돌 검사 */
	@PostMapping("/stories/crash-check")
	CrashCheckResponse crashCheck(@RequestBody StoryCrashCheckRequest storyCrashCheckRequest);

	/* 맞춤법 검사 */
	@PostMapping("/stories/spell-check")
	List<StorySpellCheckResponse> spellCheck(@RequestBody StorySpellCheckRequest storySpellCheckRequest);
}

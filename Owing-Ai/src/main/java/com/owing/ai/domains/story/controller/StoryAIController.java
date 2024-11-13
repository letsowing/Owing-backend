package com.owing.ai.domains.story.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.ai.domains.story.dto.request.CrashCheckRequest;
import com.owing.ai.domains.story.dto.request.SpellCheckRequest;
import com.owing.ai.domains.story.service.SpellCheckerService;
import com.owing.ai.domains.story.service.StoryAIService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/stories")
@RequiredArgsConstructor
public class StoryAIController {
	private final StoryAIService storyAIService;
	private final SpellCheckerService spellCheckerService;

	@PostMapping("/crash-check")
	public ResponseEntity<?> crashCheck(@RequestBody CrashCheckRequest request) {
		return ResponseEntity.ok(storyAIService.crashCheck(request));
	}

	@PostMapping("/spell-check")
	public ResponseEntity<?> spellCheck(@RequestBody SpellCheckRequest request) {
		return ResponseEntity.ok(spellCheckerService.checkSpelling(request));
	}
}

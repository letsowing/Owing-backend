package com.owing.api.story.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryFolderRepository;
import com.owing.entity.domains.story.repository.StoryRepository;

@Component
public class StoryTest {

	@Autowired
	private StoryFolderRepository storyFolderRepository;
	@Autowired
	private StoryRepository storyRepository;

	StoryFolder createStoryFolder(Long projectId) {
		return storyFolderRepository.save(
			StoryFolder.builder()
				.name("testFolder")
				.projectId(projectId)
				.position(0L)
				.build()
		);
	}

	StoryFolder createStoryFolder(Long projectId, String name, Long position) {
		return storyFolderRepository.save(
			StoryFolder.builder()
				.name(name)
				.projectId(projectId)
				.position(position)
				.build()
		);
	}

	Story createStory(StoryFolder folder) {
		Story story =
			Story.builder()
				.name("testFile1")
				.description("test")
				.folder(folder)
				.position(0L)
				.build();
		folder.getFiles().add(story);
		return storyRepository.save(story);
	}

	Story createStory(StoryFolder folder, String name, Long position) {
		Story story =
			Story.builder()
				.name(name)
				.description("test")
				.folder(folder)
				.position(position)
				.build();
		folder.getFiles().add(story);
		return storyRepository.save(story);
	}
}

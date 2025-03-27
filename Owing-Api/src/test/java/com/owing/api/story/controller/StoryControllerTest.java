package com.owing.api.story.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.api.story.model.dto.request.AddStoryContentRequest;
import com.owing.api.story.model.dto.request.UpdateStoryRequest;
import com.owing.api.utils.TestAuthUtils;
import com.owing.api.utils.TestEnvLoader;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryRepository;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Transactional("jpaTransactionManager")
@SpringBootTest
class StoryControllerTest {
	private static final String BASE_URL = "/v1/stories";

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TestAuthUtils testAuthUtils;
	@Autowired
	private StoryTest storyTest;

	private Member member;
	private Project project;
	private StoryFolder folder;
	private Long storyId;
	@Autowired
	private StoryRepository storyRepository;

	@DynamicPropertySource
	static void registerProps(DynamicPropertyRegistry registry) {
		TestEnvLoader.load(registry);
	}

	@BeforeEach
	void setUp() {
		member = testAuthUtils.createMember("test");
		project = testAuthUtils.createProject(member);
		folder = storyTest.createStoryFolder(project.getId());
		storyId = storyTest.createStory(folder).getId();
	}

	@Test
	@DisplayName("✨ 원고 내용 작성 - 성공")
	void createStory_success() throws Exception {
		// given
		String content = """
			<div style="color: yellow;">test1</div>
			<div style="color: yellow;">test2</div>
		""";

		AddStoryContentRequest request = new AddStoryContentRequest(content);

		// when
		mockMvc.perform(post(BASE_URL + "/{storyId}", storyId)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
			)
			.andExpect(status().isOk());

		// then
		Optional<Story> story = storyRepository.findById(storyId);
		assertThat(story).isPresent();
		assertThat(story.get().getContent()).isEqualTo(content);
		assertThat(story.get().getTextCount()).isEqualTo(11);
	}

	@Test
	@DisplayName("❗ 원고 내용 작성 - 존재하지 않는 storyId")
	void createStory_notFound() throws Exception {
		// given
		AddStoryContentRequest request = new AddStoryContentRequest(
			"""
					<div style="color: yellow;">test1</div>
					<div style="color: yellow;">test2</div>
				"""
		);

		// when // then
		mockMvc.perform(post(BASE_URL + "/{storyId}", 99999L)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
			)
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("✨ 원고 정보 수정 - 성공")
	void updateStory_success() throws Exception {
		// given
		UpdateStoryRequest request = new UpdateStoryRequest(
			"updatedName",
			"updatedDescription"
		);

		// when // then
		mockMvc.perform(put(BASE_URL + "/{storyId}", storyId)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
			)
			.andExpect(status().isOk());
	}

	@ParameterizedTest
	@DisplayName("❗ 원고 정보 수정 - 유효하지 않은 요청값")
	@MethodSource("invalidStoryRequests")
	void updateStory_invalidRequest(UpdateStoryRequest invalidRequest) throws Exception {
		// when // then
		mockMvc.perform(put(BASE_URL + "/{storyId}", storyId)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidRequest))
			)
			.andExpect(status().isBadRequest());
	}

	private static Stream<UpdateStoryRequest> invalidStoryRequests() {
		return Stream.of(
			new UpdateStoryRequest(null, ""),
			new UpdateStoryRequest("", null),
			new UpdateStoryRequest("a".repeat(51), null)
		);
	}

	@Test
	@DisplayName("❗ 원고 정보 수정 - 존재하지 않는 storyId")
	void updateStory_notFound() throws Exception {
		// given
		UpdateStoryRequest request = new UpdateStoryRequest(
			"updatedName",
			"updatedDescription"
		);

		// when // then
		mockMvc.perform(put(BASE_URL + "/{storyId}", 99999L)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
			)
			.andExpect(status().isNotFound());
	}

}
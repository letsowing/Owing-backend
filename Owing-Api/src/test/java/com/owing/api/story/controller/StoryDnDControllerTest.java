package com.owing.api.story.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.api.dnd.dto.common.request.CommonAddFileRequest;
import com.owing.api.dnd.dto.common.request.CommonUpdateFileNameRequest;
import com.owing.api.dnd.dto.common.request.CommonUpdateFilePositionRequest;
import com.owing.api.story.model.dto.response.StoryInfoResponse;
import com.owing.api.utils.TestAuthUtils;
import com.owing.api.utils.TestEnvLoader;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.trashcan.model.FolderType;
import com.owing.entity.domains.trashcan.repository.TrashCanFolderRepository;
import com.owing.entity.domains.trashcan.repository.TrashCanRepository;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Transactional("jpaTransactionManager")
@SpringBootTest
class StoryDnDControllerTest {
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
	private Story story;
	@Autowired
	private TrashCanFolderRepository trashCanFolderRepository;
	@Autowired
	private TrashCanRepository trashCanRepository;

	@DynamicPropertySource
	static void registerProps(DynamicPropertyRegistry registry) {
		TestEnvLoader.load(registry);
	}

	@BeforeEach
	void setUp() {
		member = testAuthUtils.createMember("test");
		project = testAuthUtils.createProject(member);
		folder = storyTest.createStoryFolder(project.getId());
		story = storyTest.createStory(folder);
	}

	@Test
	@DisplayName("✨ Story 생성 - 성공")
	void createDnd_success() throws Exception {
		// given
		CommonAddFileRequest request = new CommonAddFileRequest(
			"testFile1",
			folder.getId()
		);

		// when
		MvcResult result = mockMvc.perform(post(BASE_URL + "/dnd")
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
			)
			.andExpect(status().isOk())
			.andReturn();

		// then
		String content = result.getResponse().getContentAsString();
		StoryInfoResponse response = objectMapper.readValue(content, StoryInfoResponse.class);

		assertThat(response.storyId()).isGreaterThan(0L);
		assertThat(response.name()).isEqualTo("testFile1");
		assertThat(response.description()).isNullOrEmpty();
		assertThat(response.textCount()).isEqualTo(0);
		assertThat(response.content()).isEqualTo("");
	}

	@ParameterizedTest
	@DisplayName("❗ Story 생성 - 유효하지 않은 요청값")
	@MethodSource("invalidDndRequests")
	void createDnd_invalidRequest(CommonAddFileRequest invalidRequest) throws Exception {
		// when // then
		mockMvc.perform(post(BASE_URL + "/dnd")
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidRequest))
			)
			.andExpect(status().isBadRequest());
	}

	private static Stream<CommonAddFileRequest> invalidDndRequests() {
		return Stream.of(
			new CommonAddFileRequest(null, 1L),
			new CommonAddFileRequest("", 1L),
			new CommonAddFileRequest("a".repeat(51), 1L),
			new CommonAddFileRequest("valid", null)
		);
	}

	@Test
	@DisplayName("✨ Story 상세 조회 - 성공")
	void getDnd_success() throws Exception {
		// when
		MvcResult result = mockMvc.perform(get(BASE_URL + "/{id}", story.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member)))
			.andExpect(status().isOk())
			.andReturn();

		// then
		String content = result.getResponse().getContentAsString();
		StoryInfoResponse response = objectMapper.readValue(content, StoryInfoResponse.class);

		assertThat(response.storyId()).isEqualTo(story.getId());
		assertThat(response.name()).isEqualTo("testFile1");
		assertThat(response.description()).isEqualTo("test");
		assertThat(response.textCount()).isEqualTo(0);
		assertThat(response.content()).isEqualTo("");
	}

	@Test
	@DisplayName("❗ Story 상세 조회 - 존재하지 않는 Id")
	void getDnd_notFound() throws Exception {
		// when // then
		mockMvc.perform(get(BASE_URL + "/{id}", 99999L)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member)))
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("✨ Story 이름 변경 - 성공")
	void updateDndName_success() throws Exception {
		// given
		CommonUpdateFileNameRequest request = new CommonUpdateFileNameRequest("updatedName");

		// when // then
		mockMvc.perform(patch(BASE_URL + "/{id}/title", story.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isNoContent());
	}

	@ParameterizedTest
	@DisplayName("❗ Story 이름 변경 - 유효하지 않은 요청")
	@MethodSource("invalidFileNameRequests")
	void updateDndName_invalidRequest(CommonUpdateFileNameRequest invalidRequest) throws Exception {
		// when // then
		mockMvc.perform(patch(BASE_URL + "/{id}/title", story.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidRequest)))
			.andExpect(status().isBadRequest());
	}

	private static Stream<CommonUpdateFileNameRequest> invalidFileNameRequests() {
		return Stream.of(
			new CommonUpdateFileNameRequest(null),
			new CommonUpdateFileNameRequest(""),
			new CommonUpdateFileNameRequest(" ".repeat(1)),
			new CommonUpdateFileNameRequest("a".repeat(51))
		);
	}

	@Test
	@DisplayName("❗Story 이름 변경 - 존재하지 않는 Id")
	void updateDndName_notFound() throws Exception {
		// given
		CommonUpdateFileNameRequest request = new CommonUpdateFileNameRequest("updatedName");

		// when // then
		mockMvc.perform(patch(BASE_URL + "/{id}/title", 99999L)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("✨ Story 위치 이동 - 성공")
	void updateDndPosition_success() throws Exception {
		// given
		Long storyId1 = story.getId();
		Long storyId2 = storyTest.createStory(folder, "testFile2", 1L).getId();
		Long storyId3 = storyTest.createStory(folder, "testFile3",2L).getId();

		CommonUpdateFilePositionRequest request = new CommonUpdateFilePositionRequest(storyId1, storyId2,
			folder.getId());

		// when // then
		mockMvc.perform(patch(BASE_URL + "/{id}/position", storyId3)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("✨ Story 삭제 - 성공")
	void deleteDnd_success() throws Exception {
		// when // then
		mockMvc.perform(delete(BASE_URL + "/{id}", story.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member)))
			.andExpect(status().isNoContent());

		// trashcan
		assertThat(trashCanFolderRepository.findByItemIdAndTableName(folder.getId(), FolderType.STORY)).isPresent();
		assertThat(trashCanRepository.findByItemId(story.getId())).isPresent();
	}

	@Test
	@DisplayName("❗ Story 삭제 - 존재하지 않는 Id")
	void deleteDnd_notFound() throws Exception {
		// when // then
		mockMvc.perform(delete(BASE_URL + "/{id}", 99999L)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member)))
			.andExpect(status().isNotFound());
	}

}
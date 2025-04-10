package com.owing.api.story.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
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
import com.owing.api.dnd.dto.common.request.CommonAddFolderRequest;
import com.owing.api.dnd.dto.common.request.CommonUpdateFolderNameRequest;
import com.owing.api.dnd.dto.common.request.CommonUpdateFolderPositionRequest;
import com.owing.api.dnd.dto.common.response.CommonFileInfoResponse;
import com.owing.api.dnd.dto.common.response.CommonFolderInfoResponse;
import com.owing.api.utils.TestAuthUtils;
import com.owing.api.utils.TestEnvLoader;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryRepository;
import com.owing.entity.domains.trashcan.model.FolderType;
import com.owing.entity.domains.trashcan.repository.TrashCanFolderRepository;
import com.owing.entity.domains.trashcan.repository.TrashCanRepository;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Transactional("jpaTransactionManager")
@SpringBootTest
class StoryFolderDnDControllerTest {
	private static final String BASE_URL = "/v1/stories/folders";

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
		story = storyTest.createStory(folder);
	}

	@Test
	@DisplayName("✨ Story 폴더 생성 - 성공")
	void createDnd_success() throws Exception {
		// given
		CommonAddFolderRequest request = new CommonAddFolderRequest(
			"testFolder",
			project.getId()
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
		CommonFolderInfoResponse response = objectMapper.readValue(content, CommonFolderInfoResponse.class);

		assertThat(response.getId()).isGreaterThan(0L);
		assertThat(response.getName()).isEqualTo("testFolder");
		assertThat(response.getDescription()).isNullOrEmpty();
	}

	@ParameterizedTest
	@DisplayName("❗ Story 폴더 생성 - 유효하지 않은 요청값")
	@MethodSource("invalidDndRequests")
	void createDnd_invalidRequest(CommonAddFolderRequest invalidRequest) throws Exception {
		// when // then
		mockMvc.perform(post(BASE_URL + "/dnd")
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidRequest))
			)
			.andExpect(status().isBadRequest());
	}

	private static Stream<CommonAddFolderRequest> invalidDndRequests() {
		return Stream.of(
			new CommonAddFolderRequest(null, 1L),
			new CommonAddFolderRequest("", 1L),
			new CommonAddFolderRequest("a".repeat(51), 1L),
			new CommonAddFolderRequest("valid", null)
		);
	}

	@Test
	@DisplayName("✨ Story 폴더 상세 조회 - 성공")
	void getDnd_success() throws Exception {
		// when
		MvcResult result = mockMvc.perform(get(BASE_URL + "/{id}", folder.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member)))
			.andExpect(status().isOk())
			.andReturn();

		// then
		String content = result.getResponse().getContentAsString();
		CommonFolderInfoResponse response = objectMapper.readValue(content, CommonFolderInfoResponse.class);

		// 폴더 정보 검증
		assertThat(response.getId()).isEqualTo(folder.getId());
		assertThat(response.getName()).isEqualTo(folder.getName());
		assertThat(response.getDescription()).isEqualTo(folder.getDescription());
		assertThat(response.getProjectId()).isEqualTo(folder.getProjectId());

		// 파일 리스트 검증
		List<CommonFileInfoResponse> files = response.getFiles();
		assertThat(files).hasSize(1);

		CommonFileInfoResponse file = files.getFirst();
		assertThat(file.getId()).isEqualTo(story.getId());
		assertThat(file.getName()).isEqualTo(story.getName());
		assertThat(file.getDescription()).isEqualTo(story.getDescription());
		assertThat(file.getFolderId()).isEqualTo(folder.getId());
	}

	@Test
	@DisplayName("❗ Story 폴더 상세 조회 - 존재하지 않는 Id")
	void getDnd_notFound() throws Exception {
		// when // then
		mockMvc.perform(get(BASE_URL + "/{id}", 99999L)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member)))
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("✨ Story 폴더 이름 변경 - 성공")
	void updateDndName_success() throws Exception {
		// given
		CommonUpdateFolderNameRequest request = new CommonUpdateFolderNameRequest("updatedName");

		// when // then
		mockMvc.perform(patch(BASE_URL + "/{id}/title", folder.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isNoContent());
	}

	@ParameterizedTest
	@DisplayName("❗ Story 폴더 이름 변경 - 유효하지 않은 요청")
	@MethodSource("invalidFolderNameRequests")
	void updateDndName_invalidRequest(CommonUpdateFolderNameRequest invalidRequest) throws Exception {
		// when // then
		mockMvc.perform(patch(BASE_URL + "/{id}/title", folder.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidRequest)))
			.andExpect(status().isBadRequest());
	}

	private static Stream<CommonUpdateFolderNameRequest> invalidFolderNameRequests() {
		return Stream.of(
			new CommonUpdateFolderNameRequest(null),
			new CommonUpdateFolderNameRequest(""),
			new CommonUpdateFolderNameRequest(" ".repeat(1)),
			new CommonUpdateFolderNameRequest("a".repeat(51))
		);
	}

	@Test
	@DisplayName("❗Story 폴더 이름 변경 - 존재하지 않는 Id")
	void updateDndName_notFound() throws Exception {
		// given
		CommonUpdateFolderNameRequest request = new CommonUpdateFolderNameRequest("updatedName");

		// when // then
		mockMvc.perform(patch(BASE_URL + "/{id}/title", 99999L)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("✨ Story 폴더 위치 이동 - 성공")
	void updateDndPosition_success() throws Exception {
		// given
		Long sf1 = folder.getId();
		Long sf2 = storyTest.createStoryFolder(project.getId(), "testFolder2", 1L).getId();
		Long sf3 = storyTest.createStoryFolder(project.getId(), "testFolder3",2L).getId();

		CommonUpdateFolderPositionRequest request = new CommonUpdateFolderPositionRequest(sf1, sf2,
			project.getId());

		// when // then
		mockMvc.perform(patch(BASE_URL + "/{id}/position", sf3)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("✨ Story 폴더 삭제 - 성공")
	void deleteDnd_success() throws Exception {
		// when // then
		mockMvc.perform(delete(BASE_URL + "/{id}", folder.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member)))
			.andExpect(status().isNoContent());

		// trashcan
		assertThat(trashCanFolderRepository.findByItemIdAndTableName(folder.getId(), FolderType.STORY)).isPresent();
		assertThat(trashCanRepository.findByItemId(story.getId())).isPresent();
		assertThat(storyRepository.findById(story.getId())).isEmpty();
	}

	@Test
	@DisplayName("❗ Story 폴더 삭제 - 존재하지 않는 Id")
	void deleteDnd_notFound() throws Exception {
		// when // then
		mockMvc.perform(delete(BASE_URL + "/{id}", 99999L)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member)))
			.andExpect(status().isNotFound());
	}

}
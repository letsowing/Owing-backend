package com.owing.api.story.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.api.dnd.dto.common.request.CommonAddFolderRequest;
import com.owing.api.dnd.dto.common.request.CommonUpdateFolderPositionRequest;
import com.owing.api.dnd.dto.common.response.CommonFolderInfoResponse;
import com.owing.api.utils.TestAuthUtils;
import com.owing.api.utils.TestEnvLoader;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryFolderRepository;

import jakarta.persistence.EntityManager;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Transactional("jpaTransactionManager")
@SpringBootTest
class StoryFolderDnDShiftTest {
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
	private StoryFolder f1;
	private Story a;
	private Story b;
	private Story c;
	private StoryFolder f2;
	private StoryFolder f3;
	private StoryFolder f4;

	@Autowired
	private EntityManager em;
	@Autowired
	private StoryFolderRepository storyFolderRepository;

	@DynamicPropertySource
	static void registerProps(DynamicPropertyRegistry registry) {
		TestEnvLoader.load(registry);
	}

	@BeforeEach
	void setUp() {
		member = testAuthUtils.createMember("test");
		project = testAuthUtils.createProject(member);
		f1 = storyTest.createStoryFolder(project.getId(), "aa", 0L);
		a = storyTest.createStory(f1, "a", 0L);
		b = storyTest.createStory(f1, "b", 1L);
		c = storyTest.createStory(f1, "c", 2L);
		f2 = storyTest.createStoryFolder(project.getId(), "bb", 1L);
		f3 = storyTest.createStoryFolder(project.getId(), "cc", 2L);
		f4 = storyTest.createStoryFolder(project.getId(), "dd", 3L);
	}

	ResultActions updatePosition(StoryFolder folder, StoryFolder before, StoryFolder after, Project project) throws Exception {
		CommonUpdateFolderPositionRequest req = new CommonUpdateFolderPositionRequest(
			before == null ? null : before.getId(),
			after == null ? null : after.getId(),
			project == null ? null : project.getId()
		);
		return mockMvc.perform(patch(BASE_URL + "/{id}/position", folder.getId())
			.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(req)));
	}

	void assertPosition(Project project, StoryFolder... expected) {
		List<StoryFolder> folders = storyFolderRepository.findByParentId(project.getId());

		assertThat(folders).hasSize(expected.length);

		for (int i = 0; i < expected.length; i++) {
			StoryFolder folder = folders.get(i);
			System.out.println(folder.getName() + " : " + folder.getPosition());
			assertThat(folder.getName()).isEqualTo(expected[i].getName());
			assertThat(folder.getPosition()).isEqualTo(i);
		}
	}

	@Test
	@DisplayName("✨ 동일 폴더 - before only -> before 다음에 위치")
	void case1_beforeOnly_sameFolder() throws Exception {
		// when
		updatePosition(f2, f3, null, project)
			.andExpect(status().isNoContent());
		em.flush();
		em.clear();

		// then
		assertPosition(project, f1, f3, f2, f4);
	}

	@Test
	@DisplayName("✨ 동일 폴더 - after only -> after 이전에 위치")
	void case2_afterOnly_sameFolder() throws Exception {
		// when
		updatePosition(f3, null, f2, project)
			.andExpect(status().isNoContent());
		em.flush();
		em.clear();

		// then
		assertPosition(project, f1, f3, f2, f4);
	}

	@Test
	@DisplayName("✨ 동일 폴더 - before, after -> between 위치")
	void case3_beforeAfter_sameFolder() throws Exception {
		// when
		updatePosition(f3, f1, f2, project)
			.andExpect(status().isNoContent());
		em.flush();
		em.clear();
		// then
		assertPosition(project, f1, f3, f2, f4);
	}

	@ParameterizedTest
	@DisplayName("❗ 잘못된 위치")
	@MethodSource("invalid404Requests")
	void case4_invalid_NotFound(CommonUpdateFolderPositionRequest req) throws Exception {
		mockMvc.perform(patch(BASE_URL + "/{id}/position", f1.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
			)
			.andExpect(status().isNotFound());
	}

	private static Stream<CommonUpdateFolderPositionRequest> invalid404Requests() {
		return Stream.of(
			new CommonUpdateFolderPositionRequest(99999L, null, null),
			new CommonUpdateFolderPositionRequest(null, 99999L, null)
		);
	}

	@ParameterizedTest
	@DisplayName("❗ 잘못된 위치")
	@MethodSource("invalidPositionRequests")
	void case5_invalid_requests(String bf, String af, String fd) throws Exception {
		// given
		Project project2 = testAuthUtils.createProject(member);
		StoryFolder f5 = storyTest.createStoryFolder(project2.getId(), "ee", 0L);
		StoryFolder f6 = storyTest.createStoryFolder(project2.getId(), "ff", 1L);
		Map<String, Project> pjs = Map.of(
			"p1", project,
			"p2", project2
		);

		Map<String, StoryFolder> folders = Map.of(
			"f1", f1,
			"f2", f2,
			"f3", f3,
			"f4", f4,
			"f5", f5,
			"f6", f6
		);
		StoryFolder beforeStory = bf == null ? null : folders.get(bf);
		StoryFolder afterStory = af == null ? null : folders.get(af);
		Project pj = fd == null ? null : pjs.get(fd);

		updatePosition(f4, beforeStory, afterStory, pj)
			.andExpect(status().isBadRequest());
	}

	private static Stream<Arguments> invalidPositionRequests() {
		return Stream.of(
			Arguments.of(null, null, null),
			Arguments.of("f1", "f3", null),
			Arguments.of("f3", "f2", null),
			Arguments.of("f3", "f1", null),
			Arguments.of("f5", "f6", "p2"),
			Arguments.of("f5", null, "p2"),
			Arguments.of(null, "f6", "p2")
		);
	}

	@Test
	@DisplayName("✨ 생성 - 맨 마지막")
	void createDnd_success() throws Exception {
		// given
		CommonAddFolderRequest request = new CommonAddFolderRequest(
			"g",
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

		StoryFolder g = storyFolderRepository.findById(response.getId()).orElseThrow();
		assertThat(g.getName()).isEqualTo("g");
		assertThat(g.getPosition()).isEqualTo(4L);
	}

	@Test
	@DisplayName("✨ 삭제 - 이후 순번 당겨짐")
	void deleteDnd_success() throws Exception {
		// when
		mockMvc.perform(delete(BASE_URL + "/{id}", f1.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member)))
			.andExpect(status().isNoContent());

		em.flush();
		em.clear();

		// then
		assertPosition(project, f2, f3, f4);
	}

	@Test
	@DisplayName("✨ 복원 - 맨 마지막")
	void restoreDnd_success() {

	}

	@Test
	@DisplayName("✨ 복원 - 폴더&파일 맨 마지막")
	void restoreDnd_withFolder_success() {

	}
}
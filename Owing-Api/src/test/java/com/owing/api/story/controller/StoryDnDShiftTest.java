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
import com.owing.api.dnd.dto.common.request.CommonAddFileRequest;
import com.owing.api.dnd.dto.common.request.CommonUpdateFilePositionRequest;
import com.owing.api.story.model.dto.response.StoryInfoResponse;
import com.owing.api.utils.TestAuthUtils;
import com.owing.api.utils.TestEnvLoader;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryRepository;
import com.owing.entity.domains.trashcan.repository.TrashCanRepository;

import jakarta.persistence.EntityManager;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Transactional("jpaTransactionManager")
@SpringBootTest
class StoryDnDShiftTest {
	private static final String BASE_URL = "/v1/stories";

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private TestAuthUtils testAuthUtils;
	@Autowired
	private StoryTest storyTest;
	@Autowired
	private StoryRepository storyRepository;

	private Member member;
	private Project project;
	private StoryFolder f1;
	private Story a;
	private Story b;
	private Story c;
	private StoryFolder f2;
	private Story d;
	private Story e;
	private Story f;
	@Autowired
	private EntityManager em;
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
		f1 = storyTest.createStoryFolder(project.getId());
		a = storyTest.createStory(f1, "a", 0L);
		b = storyTest.createStory(f1, "b", 1L);
		c = storyTest.createStory(f1, "c", 2L);
		f2 = storyTest.createStoryFolder(project.getId());
		d = storyTest.createStory(f2, "d", 0L);
		e = storyTest.createStory(f2, "e", 1L);
		f = storyTest.createStory(f2, "f", 2L);
	}

	ResultActions updatePosition(Story story, Story before, Story after, StoryFolder folder) throws Exception {
		CommonUpdateFilePositionRequest req = new CommonUpdateFilePositionRequest(
			before == null ? null : before.getId(),
			after == null ? null : after.getId(),
			folder == null ? null : folder.getId()
		);
		return mockMvc.perform(patch(BASE_URL + "/{id}/position", story.getId())
			.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(req)));
	}

	void assertPosition(StoryFolder folder, Story... expected) {
		List<Story> stories = storyRepository.findByParentId(folder.getId());

		assertThat(stories).hasSize(expected.length);

		for (int i = 0; i < expected.length; i++) {
			Story story = stories.get(i);
			System.out.println(story.getName() + " : " + story.getPosition());
			assertThat(story.getName()).isEqualTo(expected[i].getName());
			assertThat(story.getPosition()).isEqualTo(i);
		}
	}

	@Test
	@DisplayName("✨ 동일 폴더 - before only -> before 다음에 위치")
	void case1_beforeOnly_sameFolder() throws Exception {
		// when
		updatePosition(b, c, null, f1)
			.andExpect(status().isNoContent());
		em.flush();
		em.clear();

		// then
		assertPosition(f1, a, c, b);
	}

	@Test
	@DisplayName("✨ 동일 폴더 - after only -> after 이전에 위치")
	void case2_afterOnly_sameFolder() throws Exception {
		// when
		updatePosition(c, null, b, f1)
			.andExpect(status().isNoContent());
		em.flush();
		em.clear();

		// then
		assertPosition(f1, a, c, b);
	}

	@Test
	@DisplayName("✨ 동일 폴더 - before, after -> between 위치")
	void case3_beforeAfter_sameFolder() throws Exception {
		// when
		updatePosition(c, a, b, f1)
			.andExpect(status().isNoContent());
		em.flush();
		em.clear();
		// then
		assertPosition(f1, a, c, b);
	}

	@Test
	@DisplayName("✨ 동일 폴더 - f1 only -> 변경점 없음")
	void case4_f1Only_sameFolderNotEmpty() throws Exception {
		// when
		updatePosition(a, null, null, f1)
			.andExpect(status().isNoContent());
		em.flush();
		em.clear();
		// then
		assertPosition(f1, a, b, c);
	}

	@Test
	@DisplayName("✨ 다른 폴더 - before only -> before 다음에 위치")
	void case5_beforeOnly_diffFolder() throws Exception {
		// when
		updatePosition(b, e, null, f2)
			.andExpect(status().isNoContent());
		em.flush();
		em.clear();
		// then
		assertPosition(f1, a, c);
		assertPosition(f2, d, e, b, f);
	}

	@Test
	@DisplayName("✨ 다른 폴더 - after only -> after 이전에 위치")
	void case6_afterOnly_diffFolder() throws Exception {
		// when
		updatePosition(b, null, e, f2)
			.andExpect(status().isNoContent());
		em.flush();
		em.clear();
		// then
		assertPosition(f1, a, c);
		assertPosition(f2, d, b, e, f);
	}

	@Test
	@DisplayName("✨ 다른 폴더 - before, after -> between 위치")
	void case7_beforeAfter_diffFolder() throws Exception {
		// when
		updatePosition(b, e, f, f2)
			.andExpect(status().isNoContent());
		em.flush();
		em.clear();
		// then
		assertPosition(f1, a, c);
		assertPosition(f2, d, e, b, f);
	}

	@Test
	@DisplayName("✨ 다른 폴더 - folder only -> 맨 마지막")
	void case8_folderOnly_diffFolderNotEmpty() throws Exception {
		// when
		updatePosition(b, null, null, f2)
			.andExpect(status().isNoContent());
		em.flush();
		em.clear();
		// then
		assertPosition(f1, a, c);
		assertPosition(f2, d, e, f, b);
	}

	@Test
	@DisplayName("✨ 다른 폴더 - folder only -> 0번 위치")
	void case9_folderOnly_diffFolderEmpty() throws Exception {
		// given
		StoryFolder f3 = storyTest.createStoryFolder(project.getId());

		// when
		updatePosition(b, null, null, f3)
			.andExpect(status().isNoContent());
		em.flush();
		em.clear();
		// then
		assertPosition(f1, a, c);
		assertPosition(f3, b);
	}

	@ParameterizedTest
	@DisplayName("❗ 잘못된 위치")
	@MethodSource("invalid404Requests")
	void case10_invalid_NotFound(CommonUpdateFilePositionRequest req) throws Exception {
		mockMvc.perform(patch(BASE_URL + "/{id}/position", a.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
			)
			.andExpect(status().isNotFound());
	}

	private static Stream<CommonUpdateFilePositionRequest> invalid404Requests() {
		return Stream.of(
			new CommonUpdateFilePositionRequest(null, null, 99999L),
			new CommonUpdateFilePositionRequest(99999L, null, null),
			new CommonUpdateFilePositionRequest(null, 99999L, null)
		);
	}

	@ParameterizedTest
	@DisplayName("❗ 잘못된 위치")
	@MethodSource("invalidPositionRequests")
	void case11_invalid_requests(String bf, String af, String fd) throws Exception {
		// given
		Map<String, Story> stories = Map.of(
			"a", a,
			"b", b,
			"c", c,
			"d", d,
			"e", e,
			"f", f
		);
		Map<String, StoryFolder> folders = Map.of(
			"f1", f1,
			"f2", f2
		);
		Story beforeStory = bf == null ? null : stories.get(bf);
		Story afterStory = af == null ? null : stories.get(af);
		StoryFolder folderStory = fd == null ? null : folders.get(fd);

		updatePosition(a, beforeStory, afterStory, folderStory)
			.andExpect(status().isBadRequest());
	}

	private static Stream<Arguments> invalidPositionRequests() {
		return Stream.of(
			Arguments.of(null, null, null),
			Arguments.of("d", "f", null),
			Arguments.of("f", "e", null),
			Arguments.of("d", "b", null),
			Arguments.of("d", "e", "f1"),
			Arguments.of("d", null, "f1"),
			Arguments.of(null, "e", "f1")
		);
	}

	@Test
	@DisplayName("✨ 생성 - 맨 마지막")
	void createDnd_success() throws Exception {
		// given
		CommonAddFileRequest request = new CommonAddFileRequest(
			"g",
			f1.getId()
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

		Story g = storyRepository.findById(response.storyId()).orElseThrow();
		assertThat(g.getName()).isEqualTo("g");
		assertThat(g.getPosition()).isEqualTo(3L);
	}

	@Test
	@DisplayName("✨ 삭제 - 이후 순번 당겨짐")
	void deleteDnd_success() throws Exception {
		// when
		mockMvc.perform(delete(BASE_URL + "/{id}", b.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member)))
			.andExpect(status().isNoContent());

		em.flush();
		em.clear();

		// then
		assertPosition(f1, a, c);
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
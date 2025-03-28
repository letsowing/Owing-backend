package com.owing.api.story.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.api.story.model.dto.response.CrashCheckItemResponse;
import com.owing.api.story.model.dto.response.CrashCheckLogResponse;
import com.owing.api.story.model.dto.response.StorySpellCheckLogResponse;
import com.owing.api.story.model.dto.response.StorySpellCheckResponse;
import com.owing.api.utils.TestAuthUtils;
import com.owing.api.utils.TestEnvLoader;
import com.owing.entity.domains.ai.log.story.model.CrashCheckLog;
import com.owing.entity.domains.ai.log.story.model.CrashCheckOutput;
import com.owing.entity.domains.ai.log.story.model.SpellCheckLog;
import com.owing.entity.domains.ai.log.story.model.SpellCheckOutput;
import com.owing.entity.domains.ai.log.story.repository.CrashCheckLogRepository;
import com.owing.entity.domains.ai.log.story.repository.SpellCheckLogRepository;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Transactional("jpaTransactionManager")
@SpringBootTest
class StoryAIControllerTest {

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
	private CrashCheckLogRepository crashCheckLogRepository;
	@Autowired
	private SpellCheckLogRepository spellCheckLogRepository;

	private Member member;
	private Project project;
	private Story story;
	private StoryFolder folder;


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
	@DisplayName("✨ AI: 설정 충돌 로그 조회 - 성공")
	void getCrashCheckLog_success() throws Exception {
		// given
		List<CrashCheckOutput> output = List.of(
			new CrashCheckOutput("base1", "add1", "reason1"),
			new CrashCheckOutput("base2", "add2", "reason2")
		);
		CrashCheckLog log = crashCheckLogRepository.save(new CrashCheckLog(story, output));

		List<CrashCheckItemResponse> items = output.stream()
			.map(o -> new CrashCheckItemResponse(o.base(), o.add(), o.reason()))
			.toList();

		CrashCheckLogResponse expected = new CrashCheckLogResponse(
			log.getId(),
			items,
			log.getCreatedAt()
		);

		// when
		MvcResult result = mockMvc.perform(get(BASE_URL + "/{storyId}/crash-check", story.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member))
			)
			.andExpect(status().isOk())
			.andReturn();

		// then
		String content = result.getResponse().getContentAsString();
		List<CrashCheckLogResponse> actual = objectMapper.readValue(content, new TypeReference<>() {});

		assertThat(actual).hasSize(1);
		assertThat(actual.getFirst())
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}


	@Test
	@DisplayName("❗ AI: 설정 충돌 로그 조회 - 존재하지 않는 원고 ID")
	void getCrashCheckLog_notFound() throws Exception {
		// when // then
		mockMvc.perform(get(BASE_URL + "/{storyId}/crash-check", 99999L)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member)))
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("✨ AI: 맞춤법 로그 조회 - 성공")
	void getSpellCheckLog_success() throws Exception {
		// given
		List<SpellCheckOutput> output = List.of(
			new SpellCheckOutput("help1", 1, 0, 0, "errMsg1", 2, "orgStr1", "candWord1"),
			new SpellCheckOutput("help2", 3, 1, 3, "errMsg2", 5, "orgStr2", "candWord2")
		);
		SpellCheckLog log = spellCheckLogRepository.save(new SpellCheckLog(story, output));

		List<StorySpellCheckResponse> items = output.stream()
			.map(o -> new StorySpellCheckResponse(
				o.help(), o.errorIdx(), o.correctMethod(), o.start(), o.errMsg(), o.end(), o.orgStr(), o.candWord()
			))
			.toList();

		StorySpellCheckLogResponse expected = new StorySpellCheckLogResponse(
			log.getId(),
			items,
			log.getCreatedAt()
		);

		// when
		MvcResult result = mockMvc.perform(get(BASE_URL + "/{storyId}/spell-check", story.getId())
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member)))
			.andExpect(status().isOk())
			.andReturn();

		String content = result.getResponse().getContentAsString();
		List<StorySpellCheckLogResponse> actual = objectMapper.readValue(content, new TypeReference<>() {
		});

		// then
		assertThat(actual).hasSize(1);
		assertThat(actual.getFirst())
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}

	@Test
	@DisplayName("❗ AI: 맞춤법 로그 조회 - 존재하지 않는 원고 ID")
	void getSpellCheckLog_notFound() throws Exception {
		// when // then
		mockMvc.perform(get(BASE_URL + "/{storyId}/spell-check", 99999L)
				.header(AUTHORIZATION, testAuthUtils.getAccessToken(member)))
			.andExpect(status().isNotFound());
	}

}
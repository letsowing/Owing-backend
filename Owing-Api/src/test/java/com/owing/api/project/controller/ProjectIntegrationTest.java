package com.owing.api.project.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.request.GenerateProjectImageRequest;
import com.owing.api.project.model.dto.request.UpdateProjectRequest;
import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Genre;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProjectIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private Long projectId;

	@Test
	@DisplayName("프로젝트 생성 - 성공")
	@Order(1)
	void testCreateProject() throws Exception {
		// given
		AddProjectRequest request = new AddProjectRequest(
			"테스트 제목1", "테스트 설명1", Category.SCENARIO_SCRIPT, Set.of(Genre.DRAMA), "http://localhost:8080/test"
		);

		// when & then
		MvcResult result = mockMvc.perform(post("/v1/projects")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.title").value("테스트 제목1"))
			.andExpect(jsonPath("$.coverUrl").value("http://localhost:8080/test"))
			.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		projectId = objectMapper.readTree(responseBody).get("id").asLong();
	}

	@Test
	@DisplayName("프로젝트 목록 - 성공")
	@Order(2)
	void testGetProjectPage() throws Exception {
		mockMvc.perform(get("/v1/projects")
				.param("page", "0")
				.param("size", "10")
				.param("projectSort", "CREATED_AT"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.projectList.content").isArray())
			.andExpect(jsonPath("$.projectList.content[0].id").value(projectId))
			.andExpect(jsonPath("$.projectList.content[0].title").value("테스트 제목1"))
			.andExpect(jsonPath("$.projectList.content[0].coverUrl").value("http://localhost:8080/test"))
			.andExpect(jsonPath("$.projectList.content[0].accessedAt").value(matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{0,6}")))
			.andExpect(jsonPath("$.projectList.content[0].createdAt").value(matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{0,6}")))
			.andExpect(jsonPath("$.projectList.content[0].updatedAt").value(matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{0,6}")));
		}

	@Test
	@DisplayName("프로젝트 상세 - 성공")
	@Order(3)
	void testGetProject() throws Exception {
		mockMvc.perform(get("/v1/projects/{projectId}", projectId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(projectId))
			.andExpect(jsonPath("$.title").value("테스트 제목1"))
			.andExpect(jsonPath("$.description").value("테스트 설명1"))
			.andExpect(jsonPath("$.category").value("SCENARIO_SCRIPT"))
			.andExpect(jsonPath("$.genres[0]").value("DRAMA"))
			.andExpect(jsonPath("$.coverUrl").value("http://localhost:8080/test"));
	}

	@Test
	@DisplayName("프로젝트 상세 - 실패")
	void testGetProjectFail() throws Exception {
		mockMvc.perform(get("/v1/projects/1"))
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("프로젝트 수정 - 성공")
	@Order(4)
	void testUpdateProject() throws Exception {
		// given
		UpdateProjectRequest request = new UpdateProjectRequest(
			"테스트 제목11", "테스트 설명11", Category.ESSAY, Set.of(Genre.ADVENTURE, Genre.DRAMA), "http://localhost:8080/test2"
		);

		// when & then
		mockMvc.perform(put("/v1/projects/{projectId}", projectId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("프로젝트 수정 조회 - 성공")
	@Order(5)
	void testUpdateProjectGet() throws Exception {
		mockMvc.perform(get("/v1/projects/{projectId}", projectId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(projectId))
			.andExpect(jsonPath("$.title").value("테스트 제목11"))
			.andExpect(jsonPath("$.description").value("테스트 설명11"))
			.andExpect(jsonPath("$.category").value("ESSAY"))
			.andExpect(jsonPath("$.genres[0]").value("ADVENTURE"))
			.andExpect(jsonPath("$.genres[1]").value("DRAMA"))
			.andExpect(jsonPath("$.coverUrl").value("http://localhost:8080/test2"));
	}

	@Test
	@DisplayName("프로젝트 삭제 - 성공")
	@Order(6)
	void testDeleteProject() throws Exception {
		mockMvc.perform(delete("/v1/projects/{projectId}", projectId))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("프로젝트 삭제 - 실패")
	void testDeleteProjectFail() throws Exception {
		mockMvc.perform(delete("/v1/projects/1"))
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("작품 presignedUrl - 성공")
	void testGetFile() throws Exception {
		// given
		String fileExtension = "jpg";

		// when & then
		mockMvc.perform(get("/v1/projects/files/{fileExtension}", fileExtension))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.presignedUrl").isString())
			.andExpect(jsonPath("$.fileUrl").isString());
	}

	@Test
	@DisplayName("프로젝트 이미지 생성 - 성공")
	void testGenerateUniverseImage() throws Exception {
		// given
		GenerateProjectImageRequest request = new GenerateProjectImageRequest(
			"테스트 제목1", "테스트 설명1", "PLAY", new String[] {"DRAMA"}
		);

		// when & then
		mockMvc.perform(post("/v1/projects/images")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.imageUrl").isString());
	}
}
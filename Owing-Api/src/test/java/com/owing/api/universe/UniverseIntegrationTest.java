package com.owing.api.universe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.OwingApiApplication;
import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.request.GenerateUniverseImageRequest;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.repository.UniverseFolderRepository;
import com.owing.entity.domains.universe.repository.UniverseRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = OwingApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ConfigurationPropertiesScan
class UniverseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UniverseRepository universeRepository;

    @Autowired
    private UniverseFolderRepository universeFolderRepository;

    private Long testUniverseId;
    private UniverseFolder testFolder;

    private static final String AUTHORIZATION = "Authorization";
    private static final String FIXED_JWT_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwibmlja25hbWUiOiLsobDrsJXsgqwiLCJwcm9maWxlVXJsIjoiaHR0cHM6Ly9lbmNyeXB0ZWQtdGJuMC5nc3RhdGljLmNvbS9pbWFnZXM_cT10Ym46QU5kOUdjUmFfaUdQcDhsRUo1ZWpaSEg3a1dfS1FCLTk4UkRab2Ewb0ZBJnMiLCJleHAiOjQ4ODY3MDQ2NzV9.04woMcXQTjKEG4bf4C1fmJeOic7DFYaElectsbDKlFtKtH7IsKZkxzSSbPtCWDvQG9ZRuDRpRgMZwlQqVsjC0w";
    private static final int NO_CONTENT = 204;
    private static final int OK = 200;
    private static final int CREATED = 201;
    private static final int NOT_FOUND = 404;

    @DynamicPropertySource
    static void loadProperties(DynamicPropertyRegistry registry) {
        Dotenv dotenv = Dotenv.configure()
                .filename("../.env") // .env 파일의 경로를 필요에 따라 지정
                .ignoreIfMissing() // .env 파일이 없으면 무시
                .load();

        dotenv.entries().forEach(entry -> {
            registry.add(entry.getKey(), entry.getValue()::toString);
        });
    }

    @BeforeEach
    void setUp() {

        testFolder = UniverseFolder.builder()
                .id(1L)
                .name("Test Folder")
                .description("Test folder description")
                .projectId(1L)
                .build();

        universeFolderRepository.save(testFolder);
    }

    @AfterEach
    void clean() {
        universeRepository.deleteAll();
        universeFolderRepository.deleteAll();
    }

    @Test
    @DisplayName("ApplicationContext 가 제대로 로드되는지 확인")
    void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @DisplayName("새로운 세계관 생성 기능 테스트")
    void testCreateUniverse() throws Exception {

        String requestUri = "/v1/universes";

        // UniverseFolder 객체가 있어야 findById 가능
        AddUniverseRequest request = new AddUniverseRequest(
                testFolder.getId(), // setup 에서 저장한 폴더 ID
                "New Universe",
                "This is a test universe",
                "http://example.com/new_universe_image.png"
        );

        String jsonContent = new ObjectMapper().writeValueAsString(request);

        //todo 수정 예정 repository 테스트로
        mockMvc.perform(post(requestUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("기존 세계관 수정 기능 테스트")
    void testUpdateUniverse() throws Exception {

        //todo 세계관 생성을 통해 만들어진 testUniverseId 가져와서 사용
        String requestUri = "/v1/universes" + testUniverseId;

        UpdateUniverseRequest request = new UpdateUniverseRequest(
                "Updated Universe",
                "Updated description",
                "http://example.com/update_universe_image.png"
        );

        String jsonContent = new ObjectMapper().writeValueAsString(request);

        //todo 수정 예정 repository 테스트로
        mockMvc.perform(put(requestUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Presigned URL 생성 기능 테스트")
    void testCreatePresignedUrl() throws Exception {

        String requestUri = "/v1/universes/files/png";

        mockMvc.perform(get(requestUri)
                        .header(AUTHORIZATION, FIXED_JWT_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("세계관 이미지 생성 기능 테스트")
    void testGenerateUniverseImage() throws Exception {

        String requestUri = "/v1/universes/images";

        GenerateUniverseImageRequest request = new GenerateUniverseImageRequest(
                "Universe AI Image",
                "Generate an image for AI universe"
        );

        String jsonContent = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(post(requestUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }
}
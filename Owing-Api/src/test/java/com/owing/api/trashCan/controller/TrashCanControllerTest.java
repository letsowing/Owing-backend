package com.owing.api.trashCan.controller;

import com.owing.OwingApiApplication;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.trashcan.repository.TrashCanRepository;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;
import com.owing.entity.folders.trashcan.repository.TrashCanFolderRepository;
import io.github.cdimascio.dotenv.Dotenv;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = OwingApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ConfigurationPropertiesScan
@Transactional
class TrashCanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrashCanRepository trashCanRepository;

    @Autowired
    private TrashCanFolderRepository trashCanFolderRepository;

    private static final String AUTHORIZATION = "Authorization";
    // 제공된 고정 JWT 토큰
    private static final String FIXED_JWT_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwibmlja25hbWUiOiLsobDrsJXsgqwiLCJwcm9maWxlVXJsIjoiaHR0cHM6Ly9lbmNyeXB0ZWQtdGJuMC5nc3RhdGljLmNvbS9pbWFnZXM_cT10Ym46QU5kOUdjUmFfaUdQcDhsRUo1ZWpaSEg3a1dfS1FCLTk4UkRab2Ewb0ZBJnMiLCJleHAiOjQ4ODY3MDQ2NzV9.04woMcXQTjKEG4bf4C1fmJeOic7DFYaElectsbDKlFtKtH7IsKZkxzSSbPtCWDvQG9ZRuDRpRgMZwlQqVsjC0w";
    private static final int NO_CONTENT = 204;
    private static final int OK = 200;
    private static final int CREATED = 201;
    private static final int NOT_FOUND = 404;

    private Long userId;
    private Long trashCanId;
    private String randomString;

    // @DynamicPropertySource를 사용하여 .env 파일 로드
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
        // 랜덤 문자열 생성
        randomString = UUID.randomUUID().toString().substring(0, 6);

        userId = 1L;

        // TrashCanFolder 생성 및 저장
        TrashCanFolder trashCanFolder = TrashCanFolder.builder()
                .name("Test Folder " + randomString)
                .build();
        trashCanFolderRepository.save(trashCanFolder);

        // TrashCan 엔티티 생성 및 저장
        TrashCan trashCan = TrashCan.builder()
                .itemId(100L) // 예시 itemId, 실제 값으로 수정
                .name("Sample Trash Name " + randomString)
                .description("Sample Trash Description")
                .imageUrl("http://example.com/image.jpg")
                .trashCanFolder(trashCanFolder)
                .build();
        trashCanRepository.save(trashCan);

        // TRASHCAN_ID 설정 (ID를 인코딩)
        trashCanId = trashCan.getId();

        //todo trashCanId가 null로 설정되어서 해결해야 함
        System.out.println(trashCanId);
    }

    @Test
    @DisplayName("ApplicationContext가 제대로 로드되는지 확인")
    void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @DisplayName("쓰레기 영구 삭제가 정상적으로 작동한다.")
    void testDeleteTrashCan() throws Exception {
        // given
        String requestUri = "/v1/trashcans/" + trashCanId;

        // when & then
        mockMvc.perform(delete(requestUri)
                        .header(AUTHORIZATION, FIXED_JWT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("쓰레기 복원이 정상적으로 작동한다.")
    void testRestoreTrashCan() throws Exception {
        // given
        String requestUri = "/v1/trashcans/" + trashCanId;

        // when & then
        mockMvc.perform(post(requestUri)
                        .header(AUTHORIZATION, FIXED_JWT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("원고 쓰레기 상세보기가 정상적으로 작동한다.")
    void testGetStoryTrashCan() throws Exception {
        // given
        String requestUri = "/v1/trashcans/" + trashCanId + "/story";

        // when
        String expectedContent = "Sample Trash Content"; // 실제 기대하는 내용으로 수정
        mockMvc.perform(get(requestUri)
                        .header(AUTHORIZATION, FIXED_JWT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains(expectedContent));
    }

    @Test
    @DisplayName("인물 쓰레기 상세보기가 정상적으로 작동한다.")
    void testGetCastTrashCan() throws Exception {
        // given
        String requestUri = "/v1/trashcans/" + trashCanId + "/cast";

        // when
        String expectedContent = "Sample Trash Content"; // 실제 기대하는 내용으로 수정
        mockMvc.perform(get(requestUri)
                        .header(AUTHORIZATION, FIXED_JWT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains(expectedContent));
    }

    @Test
    @DisplayName("세계관 쓰레기 상세보기가 정상적으로 작동한다.")
    void testGetUniverseTrashCan() throws Exception {
        // given
        String requestUri = "/v1/trashcans/" + trashCanId + "/universe";

        // when
        String expectedContent = "Sample Trash Content"; // 실제 기대하는 내용으로 수정
        mockMvc.perform(get(requestUri)
                        .header(AUTHORIZATION, FIXED_JWT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains(expectedContent));
    }

    // 필요에 따라 추가 테스트 메서드를 작성할 수 있습니다.
}

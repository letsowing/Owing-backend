package com.owing.api.universe.controller;

import static com.owing.common.constant.TokenConst.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.OwingApiApplication;
import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.request.GenerateUniverseImageRequest;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.common.util.JwtUtils;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.OauthProvider;
import com.owing.entity.domains.member.repository.MemberRepository;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.repository.UniverseFolderRepository;
import com.owing.entity.domains.universe.repository.UniverseRepository;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootTest(
        classes = OwingApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ConfigurationPropertiesScan
@Transactional("jpaTransactionManager")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UniverseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UniverseRepository universeRepository;

    @Autowired
    private UniverseFolderRepository universeFolderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtUtils jwtUtils;

    private String jwtToken;
    private Universe testUniverse;
    private UniverseFolder testUniverseFolder;

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

        // test 맴버 생성
        Member member = Member.builder()
                .id(1L)
                .email("user@example.com")
                .password("securePassword123")
                .name("홍길동")
                .nickname("hongGilDong")
                .phoneNumber("010-1234-5678")
                .profileUrl("http://example.com/profile/honggildong")
                .provider(OauthProvider.KAKAO) // 예시로 GitHub OAuth 사용
                .build();

        memberRepository.save(member);

        jwtToken = BEARER_TYPE_SPACE + jwtUtils.generateAccessToken(member);

        // Test 폴더 저장
        testUniverseFolder = UniverseFolder.positionBuilder()
                .name("Test Folder")
                .description("Test folder description")
                .projectId(1L)
                .position(1L)
                .build();

        universeFolderRepository.save(testUniverseFolder);

        testUniverse = Universe.builder()
                .name("Test Universe")
                .description("This is a test universe description.")
                .imageUrl("http://example.com/test_universe_image.png")
                .position(1L)
                .folder(testUniverseFolder)
                .build();

        universeRepository.save(testUniverse);
    }

    @Test
    @Order(1)
    @DisplayName("ApplicationContext 가 제대로 로드되는지 확인")
    void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("새로운 세계관 생성 기능 테스트")
    void testCreateUniverse() throws Exception {

        String requestUri = "/v1/universes";

        // UniverseFolder 객체가 있어야 findById 가능
        AddUniverseRequest request = new AddUniverseRequest(
                testUniverseFolder.getId(), // setup 에서 저장한 폴더 ID
                "New Universe",
                "This is a test universe",
                "http://example.com/new_universe_image.png"
        );

        String jsonContent = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(requestUri)
                        .header(REQUEST_HEADER_AUTH, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Universe"))
                .andExpect(jsonPath("$.description").value("This is a test universe"))
                .andExpect(jsonPath("$.imageUrl").value("http://example.com/new_universe_image.png"))
                .andReturn();
    }

    @Test
    @Order(3)
    @DisplayName("유효하지 않은 요청 값으로 Universe 생성 시 예외 발생 확인 - folderId가 null 인 경우")
    void testInvalidCreateUniverseRequest_FolderIdNull() throws Exception {

        String requestUri = "/v1/universes";

        // folderId가 null 인 경우
        AddUniverseRequest invalidRequest = new AddUniverseRequest(
                null,
                "Test Universe",
                "This is a test universe",
                "http://example.com/test_universe_image.png"
        );

        String jsonContent = objectMapper.writeValueAsString(invalidRequest);

        mockMvc.perform(post(requestUri)
                        .header(REQUEST_HEADER_AUTH, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest())  // 400 Bad Request
                .andExpect(jsonPath("$.description").value("소속되는 folderId는 필수적으로 들어가야 합니다."));  // 오류 메시지 확인
    }

    @Test
    @Order(4)
    @DisplayName("기존 세계관 수정 기능 테스트")
    void testUpdateUniverse() throws Exception {

        // 세계관 생성을 통해 만들어진 testUniverseId 가져와서 사용
        String requestUri = "/v1/universes/" + testUniverse.getId();

        UpdateUniverseRequest request = new UpdateUniverseRequest(
                "Updated Universe",
                "Updated description",
                "http://example.com/update_universe_image.png"
        );

        String jsonContent = objectMapper.writeValueAsString(request);

        mockMvc.perform(put(requestUri)
                        .header(REQUEST_HEADER_AUTH, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Universe"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.imageUrl").value("http://example.com/update_universe_image.png"));
    }

    @Test
    @Order(5)
    @DisplayName("유효하지 않은 요청 값으로 Universe 수정 시 예외 발생 확인")
    void testInvalidUpdateUniverseRequest() throws Exception {

        String requestUri = "/v1/universes/" + testUniverse.getId();

        // name 이 빈 문자열인 경우
        UpdateUniverseRequest invalidRequest1 = new UpdateUniverseRequest(
                "",
                "Valid Description",
                "http://example.com/test_universe_image.png"
        );

        // description 이 빈 문자열인 경우
        UpdateUniverseRequest invalidRequest2 = new UpdateUniverseRequest(
                "Valid Universe Name",
                "",
                "http://example.com/test_universe_image.png"
        );

        String jsonContent1 = objectMapper.writeValueAsString(invalidRequest1);
        String jsonContent2 = objectMapper.writeValueAsString(invalidRequest2);

        // name 이 빈 문자열인 경우
        mockMvc.perform(put(requestUri)
                        .header(REQUEST_HEADER_AUTH, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent1))
                .andExpect(status().isBadRequest())  // 400 Bad Request
                .andExpect(jsonPath("$.description").value("세계관 이름은 필수적으로 들어가야합니다."));  // 오류 메시지 확인

        // description 이 빈 문자열인 경우
        mockMvc.perform(put(requestUri)
                        .header(REQUEST_HEADER_AUTH, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent2))
                .andExpect(status().isBadRequest())  // 400 Bad Request
                .andExpect(jsonPath("$.description").value("세계관 설명은 필수적으로 들어가야합니다."));  // 오류 메시지 확인
    }

    @Test
    @Order(6)
    @DisplayName("Presigned URL 생성 기능 테스트")
    void testCreatePresignedUrl() throws Exception {

        String requestUri = "/v1/universes/files/png";

        mockMvc.perform(get(requestUri)
                    .header(REQUEST_HEADER_AUTH, jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    @DisplayName("세계관 이미지 생성 기능 테스트")
    void testGenerateUniverseImage() throws Exception {

        String requestUri = "/v1/universes/images";

        GenerateUniverseImageRequest request = new GenerateUniverseImageRequest(
                "Universe AI Image",
                "Generate an image for AI universe"
        );

        String jsonContent = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(requestUri)
                        .header(REQUEST_HEADER_AUTH, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    @Order(8)
    @DisplayName("유효하지 않은 요청 값으로 Universe 이미지 생성 요청 시 예외 발생 확인")
    void testInvalidGenerateUniverseImageRequest() throws Exception {

        String requestUri = "/v1/universes/generate-image";

        // name 이 빈 문자열인 경우
        GenerateUniverseImageRequest invalidRequest1 = new GenerateUniverseImageRequest(
                "",
                "Valid Description"
        );

        // description 이 빈 문자열인 경우
        GenerateUniverseImageRequest invalidRequest2 = new GenerateUniverseImageRequest(
                "Valid Universe Name",
                ""
        );

        String jsonContent1 = objectMapper.writeValueAsString(invalidRequest1);
        String jsonContent2 = objectMapper.writeValueAsString(invalidRequest2);

        // name 이 빈 문자열인 경우
        mockMvc.perform(post(requestUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent1))
                .andExpect(status().isBadRequest())  // 400 Bad Request
                .andExpect(jsonPath("$.description").value("세계관 이름은 필수적으로 들어가야 합니다."));  // 오류 메시지 확인

        // description 이 빈 문자열인 경우
        mockMvc.perform(post(requestUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent2))
                .andExpect(status().isBadRequest())  // 400 Bad Request
                .andExpect(jsonPath("$.description").value("세계관 설명은 필수적으로 들어가야 합니다."));  // 오류 메시지 확인
    }
}
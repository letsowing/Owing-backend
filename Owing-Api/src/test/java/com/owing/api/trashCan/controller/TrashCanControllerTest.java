package com.owing.api.trashCan.controller;

import com.owing.api.common.util.JwtUtils;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.OauthProvider;
import com.owing.entity.domains.member.repository.MemberRepository;
import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Genre;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.model.ProjectInfo;
import com.owing.entity.domains.project.repository.ProjectRepository;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryContent;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryFolderRepository;
import com.owing.entity.domains.story.repository.StoryRepository;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.trashcan.repository.TrashCanRepository;
import com.owing.entity.folders.trashcan.model.FolderType;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;
import com.owing.entity.folders.trashcan.repository.TrashCanFolderRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static com.owing.api.common.constant.TokenConst.BEARER_TYPE_SPACE;
import static com.owing.api.common.constant.TokenConst.REQUEST_HEADER_AUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional("jpaTransactionManager")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TrashCanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrashCanRepository trashCanRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private StoryFolderRepository storyFolderRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TrashCanFolderRepository trashCanFolderRepository;

    private String jwtToken;
    private Long trashCanId;
    private String randomString;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DataSource dataSource; // DataSource 주입 추가

    // 데이터베이스 정보 출력 메서드 추가
    private void printDatabaseInfo() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String dbProductName = metaData.getDatabaseProductName();
            String dbProductVersion = metaData.getDatabaseProductVersion();
            System.out.println("JPA가 사용 중인 데이터베이스: " + dbProductName + " " + dbProductVersion);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("데이터베이스 정보를 가져오는 데 실패했습니다.");
        }
    }

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
        printDatabaseInfo();
        // 랜덤 문자열 생성
        randomString = UUID.randomUUID().toString().substring(0, 6);

        // StoryFolder 생성 및 저장
        StoryFolder storyFolder = StoryFolder.builder()
                .projectId(1L)
                .name("Sample Story Folder " + randomString)
                .description("This is a sample story folder description")
                .position(1L)
                .build();
        storyFolderRepository.save(storyFolder);

        // StoryContent 생성
        StoryContent storyContent = StoryContent.builder()
                .content("This is a sample story content. " + randomString)
                .build();

        // Story 엔티티 생성 및 저장
        Story story = Story.builder()
                .name("Sample Story " + randomString)
                .description("This is a sample story description")
                .position(1L)
                .textCount(100)
                .folder(storyFolder)
                .storyContent(storyContent)
                .build();
        storyRepository.save(story);

        // test 맴버 생성
        Member member = Member.builder()
                .email("user@example.com")
                .password("securePassword123")
                .name("홍길동")
                .nickname("honggildong")
                .phoneNumber("010-1234-5678")
                .profileUrl("http://example.com/profile/honggildong")
                .provider(OauthProvider.KAKAO) // 예시로 GitHub OAuth 사용
                .build();

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        jwtToken = BEARER_TYPE_SPACE + jwtUtils.generateAccessToken(member);

        // 1. Project 먼저 저장
        Project project = Project.builder()
                .projectInfo(ProjectInfo.builder()
                        .title("Test Project Title")
                        .description("Test Project Description")
                        .category(Category.ESSAY)
                        .genres(Set.of(Genre.ACTION))
                        .coverUrl("http://test-cover.url")
                        .build())
                .member(member)
                .build();
        projectRepository.save(project);  // 먼저 저장

        // 2. TrashCanFolder 저장
        TrashCanFolder trashCanFolder = TrashCanFolder.builder()
                .itemId(story.getId())
                .tableName(FolderType.CAST)
                .name("Test Folder " + randomString)
                .project(project)
                .trashCanList(new ArrayList<>())
                .build();
        trashCanFolderRepository.save(trashCanFolder);

        // TrashCan 엔티티 생성 및 저장
        TrashCan trashCan = TrashCan.builder()
                .itemId(story.getId()) // 예시 itemId, 실제 값으로 수정
                .name("Sample Trash Name " + randomString)
                .description("Sample Trash Description")
                .imageUrl("http://example.com/image.jpg")
                .trashCanFolder(trashCanFolder)
                .build();
        trashCanRepository.save(trashCan);

        // TRASHCAN_ID 설정 (ID를 인코딩)
        trashCanId = trashCan.getId();
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
                        .header(REQUEST_HEADER_AUTH, jwtToken)
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
                        .header(REQUEST_HEADER_AUTH, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("원고 쓰레기 상세보기가 정상적으로 작동한다.")
    void testGetStoryTrashCan() throws Exception {
        // given
        String requestUri = "/v1/trashcans/" + trashCanId + "/story";

        // when
        String expectedContent = "This is a sample story"; // 실제 기대하는 내용으로 수정
        mockMvc.perform(get(requestUri)
                        .header(REQUEST_HEADER_AUTH, jwtToken)
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
        String expectedContent = "This is a sample cast"; // 실제 기대하는 내용으로 수정
        mockMvc.perform(get(requestUri)
                        .header(REQUEST_HEADER_AUTH, jwtToken)
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
        String expectedContent = "This is a sample universe"; // 실제 기대하는 내용으로 수정
        mockMvc.perform(get(requestUri)
                        .header(REQUEST_HEADER_AUTH, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains(expectedContent));
    }

    // 필요에 따라 추가 테스트 메서드를 작성할 수 있습니다.
}

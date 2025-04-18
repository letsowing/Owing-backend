package com.owing.api.project.service;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.response.ProjectInfoResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoPageResponse;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.common.constant.ProjectSort;
import com.owing.common.util.MemberUtils;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.OauthProvider;
import com.owing.entity.domains.member.repository.MemberRepository;
import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.repository.ProjectRepository;
import com.owing.entity.domains.story.repository.StoryFolderRepository;
import com.owing.entity.domains.universe.repository.UniverseFolderRepository;
import com.owing.node.domains.project.repository.ProjectNodeRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.owing.common.constant.OwingApiConst.PAGE_DEFAULT;
import static com.owing.common.constant.OwingApiConst.PAGE_SIZE_DEFAULT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class ReadProjectUseCaseTest {

    private Member testMember;

    @MockBean
    private MemberUtils memberUtils;
    @SpyBean
    private ProjectNodeRepository projectNodeRepository;

    @Autowired
    private CreateProjectUseCase createProjectUseCase;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private StoryFolderRepository storyFolderRepository;
    @Autowired
    private UniverseFolderRepository universeFolderRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private Neo4jClient neo4jClient;
    @Autowired
    private ReadProjectListUseCase readProjectListUseCase;
    @Autowired
    private ReadProjectUseCase readProjectUseCase;

    @DynamicPropertySource
    static void loadProperties(DynamicPropertyRegistry registry) {
        Dotenv dotenv = Dotenv.configure()
                .directory("../")
                .filename(".env") // .env 파일의 경로를 필요에 따라 지정
                .ignoreIfMissing() // .env 파일이 없으면 무시
                .load();

        dotenv.entries().forEach(entry -> {
            registry.add(entry.getKey(), entry.getValue()::toString);
        });
    }

    @BeforeEach
    void setUp() {
        testMember = Member.builder()
                .email("test@integration.com")
                .password("integration1234")
                .name("테스트유저")
                .nickname("integUser")
                .provider(OauthProvider.KAKAO)
                .build();
        memberRepository.save(testMember);

        Mockito.reset(memberUtils, projectNodeRepository);
    }

    @AfterEach
    void tearDown() {
        storyFolderRepository.deleteAllInBatch();
        universeFolderRepository.deleteAllInBatch();
        projectRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();

        if (neo4jClient == null) {
            throw new IllegalStateException("Neo4jClient is null, cannot clean up Neo4j DB.");
        }
        neo4jClient.query("MATCH (n) DETACH DELETE n;").run();
    }

    @Test
    @DisplayName("프로젝트 목록이 페이징되어 조회된다.")
    void readProjectList() {
        // given
        when(memberUtils.getCurrentMemberReference()).thenReturn(testMember);
        when(memberUtils.getCurrentMemberId()).thenReturn(testMember.getId());
        for (int i = 1; i <= 20; i++) {
            createProjectUseCase.execute(new AddProjectRequest(
                    "Test 프로젝트" + i,
                    "Test Desc" + i,
                    Category.ESSAY,
                    new HashSet<>(),
                    null
            ));
        }

        int defaultPage = Integer.parseInt(PAGE_DEFAULT);
        int defaultSize = Integer.parseInt(PAGE_SIZE_DEFAULT);

        // when
        ProjectShortInfoPageResponse projectList = readProjectListUseCase.execute(
                defaultPage,
                defaultSize,
                ProjectSort.ACCESSED_AT
        );

        // then
        assertThat(projectList.projectList()).hasSize(defaultSize);
    }

    // 없을 경우 0개
    @Test
    @DisplayName("프로젝트가 없는 사용자라면 빈 리스트가 조회된다.")
    void readProjectListWithEmptyList() {
        // given
        when(memberUtils.getCurrentMemberReference()).thenReturn(testMember);
        when(memberUtils.getCurrentMemberId()).thenReturn(testMember.getId());

        int defaultPage = Integer.parseInt(PAGE_DEFAULT);
        int defaultSize = Integer.parseInt(PAGE_SIZE_DEFAULT);

        // when
        ProjectShortInfoPageResponse projectList = readProjectListUseCase.execute(
                defaultPage,
                defaultSize,
                ProjectSort.ACCESSED_AT
        );

        // then
        assertThat(projectList.projectList()).isEmpty();
    }

    @Test
    @DisplayName("프로젝트가 상세 정보가 조회된다.")
    void readProject() {
        // given
        when(memberUtils.getCurrentMemberReference()).thenReturn(testMember);
        when(memberUtils.getCurrentMemberId()).thenReturn(testMember.getId());

        List<ProjectShortInfoResponse> savedProjectList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            ProjectShortInfoResponse savedProject = createProjectUseCase.execute(new AddProjectRequest(
                    "Test 프로젝트" + i,
                    "Test Desc" + i,
                    Category.ESSAY,
                    new HashSet<>(),
                    null
            ));
            savedProjectList.add(savedProject);
        }

        long targetProjectId = savedProjectList.getLast().project().id();

        // when
        ProjectInfoResponse result = readProjectUseCase.execute(targetProjectId);

        // then
        assertThat(result.id()).isEqualTo(targetProjectId);
    }
}
package com.owing.api.project.service;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.common.util.MemberUtils;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.OauthProvider;
import com.owing.entity.domains.member.repository.MemberRepository;
import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.repository.ProjectRepository;
import com.owing.entity.domains.story.repository.StoryFolderRepository;
import com.owing.entity.domains.universe.repository.UniverseFolderRepository;
import com.owing.node.domains.project.model.ProjectNode;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class CreateProjectUseCaseTest {

    @MockBean
    private MemberUtils memberUtils;
    @SpyBean
    private ProjectNodeRepository projectNodeRepository;

    @Autowired
    private CreateProjectUseCase createProjectUseCase;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProjectRepository projectRepository;

    private Member testMember;
    @Autowired
    private Neo4jClient neo4jClient;
    @Autowired
    private StoryFolderRepository storyFolderRepository;
    @Autowired
    private UniverseFolderRepository universeFolderRepository;

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
        // 테스트 전용 멤버를 직접 DB에 저장
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
    @DisplayName("Project 생성 시 Entuty와 Node가 함께 생성된다.")
    void createProjectUseCase() {
        // given
        when(memberUtils.getCurrentMemberReference())
                .thenReturn(testMember);

        AddProjectRequest request = new AddProjectRequest(
                "Integration Project",
                "Integration Test Desc",
                Category.ESSAY,
                new HashSet<>(),
                null
        );

        // when
        ProjectShortInfoResponse res = createProjectUseCase.execute(request);

        // then
        assertThat(res.project().title()).isEqualTo(request.title());

        Optional<Project> savedProject = projectRepository.findById(res.project().id());
        assertThat(savedProject).isPresent();

        Optional<ProjectNode> savedProjectNode = projectNodeRepository.findById(res.project().id());
        assertThat(savedProjectNode).isPresent();
    }

    @Test
    @DisplayName("Project 생성 시 Node생성이 실페하면 모두 롤백된다.")
    void createProjectUseCaseWithNodeFail() {
        // given
        when(memberUtils.getCurrentMemberReference())
                .thenReturn(testMember);
        doThrow(new RuntimeException()).when(projectNodeRepository).save(any(ProjectNode.class));

        AddProjectRequest request = new AddProjectRequest(
                "Integration Project",
                "Integration Test Desc",
                Category.ESSAY,
                new HashSet<>(),
                null
        );

        // when, then
        assertThatThrownBy(() -> createProjectUseCase.execute(request))
                .isInstanceOf(RuntimeException.class);

        List<Project> projects = projectRepository.findAll();
        assertThat(projects).isEmpty();

        List<ProjectNode> projectNodes = projectNodeRepository.findAll();
        assertThat(projectNodes).isEmpty();
    }

}
package com.owing.api.project.service;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.common.util.MemberUtils;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.OauthProvider;
import com.owing.entity.domains.member.repository.MemberRepository;
import com.owing.entity.domains.project.error.ProjectErrorCode;
import com.owing.entity.domains.project.error.exception.ProjectIllegalAccessException;
import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.repository.ProjectRepository;
import com.owing.entity.domains.story.repository.StoryFolderRepository;
import com.owing.entity.domains.universe.repository.UniverseFolderRepository;
import com.owing.node.domains.project.adapter.ProjectNodeAdapter;
import com.owing.node.domains.project.error.code.ProjectNodeErrorCode;
import com.owing.node.domains.project.error.exception.ProjectNodeNotFoundException;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class DeleteProjectUseCaseTest {

    @MockBean
    private MemberUtils memberUtils;
    @SpyBean
    private ProjectNodeRepository projectNodeRepository;

    @Autowired
    private DeleteProjectUseCase deleteProjectUseCase;
    @Autowired
    private CreateProjectUseCase createProjectUseCase;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProjectRepository projectRepository;

    private Member testMember;
    @Autowired
    private ProjectNodeAdapter projectNodeAdapter;
    @Autowired
    private Neo4jClient neo4jClient;
    @Autowired
    private StoryFolderRepository storyFolderRepository;
    @Autowired
    private UniverseFolderRepository universeFolderRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        jdbcTemplate.execute("DELETE FROM project");
        memberRepository.deleteAllInBatch();

        if (neo4jClient == null) {
            throw new IllegalStateException("Neo4jClient is null, cannot clean up Neo4j DB.");
        }
        neo4jClient.query("MATCH (n) DETACH DELETE n;").run();
    }


    @Test
    @DisplayName("Project 삭제 시 Entuty와 Node가 함께 삭제된다.")
    void deleteProjectUseCase() {
        // given
        when(memberUtils.getCurrentMemberReference())
                .thenReturn(testMember);
        when(memberUtils.getCurrentMemberId())
                .thenReturn(testMember.getId());
        ProjectShortInfoResponse projectInfo = createProjectUseCase.execute(new AddProjectRequest(
                "Integration Project",
                "Integration Test Desc",
                Category.ESSAY,
                new HashSet<>(),
                null
        ));
        Long projectId = projectInfo.project().id();

        // when
        deleteProjectUseCase.execute(projectId);

        // then
        Optional<Project> projects = projectRepository.findById(projectId);
        assertThat(projects).isEmpty();

        assertThatThrownBy(() -> projectNodeAdapter.findById(projectId))
                .isInstanceOf(ProjectNodeNotFoundException.class)
                .hasMessage(ProjectNodeErrorCode.PROJECT_NODE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("Project 삭제 시 작성자와 다른 유저라면 예외가 발생한다.")
    void deleteProjectUseCaseWithIllegalMember() {
        // given
        when(memberUtils.getCurrentMemberReference())
                .thenReturn(testMember);
        when(memberUtils.getCurrentMemberId())
                .thenReturn(0L);
        ProjectShortInfoResponse projectInfo = createProjectUseCase.execute(new AddProjectRequest(
                "Integration Project",
                "Integration Test Desc",
                Category.ESSAY,
                new HashSet<>(),
                null
        ));
        Long projectId = projectInfo.project().id();

        // when, then
        assertThatThrownBy(() -> deleteProjectUseCase.execute(projectId))
                .isInstanceOf(ProjectIllegalAccessException.class)
                .hasMessage(ProjectErrorCode.ILLEGAL_ACCESS.getMessage());
    }
}
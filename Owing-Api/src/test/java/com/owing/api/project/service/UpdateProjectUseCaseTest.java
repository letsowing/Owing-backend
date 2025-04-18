package com.owing.api.project.service;

import com.owing.api.project.model.dto.request.AddProjectRequest;
import com.owing.api.project.model.dto.request.UpdateProjectRequest;
import com.owing.api.project.model.dto.response.ProjectShortInfoResponse;
import com.owing.common.util.MemberUtils;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.OauthProvider;
import com.owing.entity.domains.member.repository.MemberRepository;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.error.ProjectErrorCode;
import com.owing.entity.domains.project.error.exception.ProjectIllegalAccessException;
import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Project;
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

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class UpdateProjectUseCaseTest {

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
    private UpdateProjectUseCase updateProjectUseCase;
    @Autowired
    private ProjectAdapter projectAdapter;

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
    @DisplayName("프로젝트 정보가 정상적으로 수정된다.")
    void updateProjectSuccess() {
        // given
        when(memberUtils.getCurrentMemberReference()).thenReturn(testMember);
        when(memberUtils.getCurrentMemberId()).thenReturn(testMember.getId());

        ProjectShortInfoResponse created = createProjectUseCase.execute(
                new AddProjectRequest(
                        "Original Title",
                        "Original Desc",
                        Category.ESSAY,
                        new HashSet<>(),
                        null
                )
        );
        Long projectId = created.project().id();

        UpdateProjectRequest request = new UpdateProjectRequest(
                "Updated Title",
                "Updated Desc",
                Category.ESSAY,
                new HashSet<>(),
                null
        );

        // when
        updateProjectUseCase.execute(projectId, request);

        // then
        Project updatedProject = projectAdapter.findById(projectId);
        assertThat(created.project().id()).isEqualTo(updatedProject.getId());
        assertThat(updatedProject.getProjectInfo().getTitle()).isEqualTo(request.title());
        assertThat(updatedProject.getProjectInfo().getDescription()).isEqualTo(request.description());
    }

    @Test
    @DisplayName("유효하지 않은 사용자가 수정 요청 시 예외가 발생한다.")
    void updateProjectIllegalAccess() {
        // given
        when(memberUtils.getCurrentMemberReference()).thenReturn(testMember);
        ProjectShortInfoResponse created = createProjectUseCase.execute(
                new AddProjectRequest(
                        "Title",
                        "Desc",
                        Category.ESSAY,
                        new HashSet<>(),
                        null
                )
        );
        Long projectId = created.project().id();

        UpdateProjectRequest updateReq = new UpdateProjectRequest(
                "X","Y",
                Category.ESSAY,
                new HashSet<>(),
                null
        );

        when(memberUtils.getCurrentMemberId()).thenReturn(0L);
        // when, then
        assertThatThrownBy(() -> updateProjectUseCase.execute(projectId, updateReq))
                .isInstanceOf(ProjectIllegalAccessException.class)
                .hasMessage(ProjectErrorCode.ILLEGAL_ACCESS.getMessage());
    }
}
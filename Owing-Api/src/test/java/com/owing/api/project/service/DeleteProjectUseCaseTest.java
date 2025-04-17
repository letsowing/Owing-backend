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
import com.owing.node.domains.project.adapter.ProjectNodeAdapter;
import com.owing.node.domains.project.error.code.ProjectNodeErrorCode;
import com.owing.node.domains.project.error.exception.ProjectNodeNotFoundException;
import com.owing.node.domains.project.repository.ProjectNodeRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional("jpaTransactionManager")
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

        Mockito.reset();
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
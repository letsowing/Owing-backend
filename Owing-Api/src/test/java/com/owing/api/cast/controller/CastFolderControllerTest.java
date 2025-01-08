package com.owing.api.cast.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.api.cast.model.dto.request.UpdateCastFolderInfo;
import com.owing.api.common.util.JwtUtils;
import com.owing.api.project.model.mapper.ProjectNodeMapper;
import com.owing.common.error.code.GlobalErrorCode;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.OauthProvider;
import com.owing.entity.domains.member.repository.MemberRepository;
import com.owing.entity.domains.project.error.ProjectErrorCode;
import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Genre;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.model.ProjectInfo;
import com.owing.entity.domains.project.repository.ProjectRepository;
import com.owing.node.domains.project.error.code.ProjectNodeErrorCode;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.domains.project.repository.ProjectNodeRepository;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional("jpaTransactionManager")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@AutoConfigureMockMvc
class CastFolderControllerTest {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_TYPE_SPACE = "Bearer ";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectNodeMapper projectNodeMapper;
    @Autowired
    private ProjectNodeRepository projectNodeRepository;
    @Autowired
    private CastFolderNodeRepository castFolderNodeRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private ObjectMapper objectMapper;

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

    @AfterEach
    void cleanDatabase() {
        // JpaTxManager사용으로 인해 neo4j의 rollback이 되지 않아 임의로 클렌징을 진행
        projectNodeRepository.deleteAll();
    }

    @DisplayName("CastFolder가 없다면 빈 리스트를 반환한다")
    @Test
    void getEmptyFolderDropdownList() throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .get(String.format("/v1/cast/folders/%d/dropdown", projectNode.getId()))
                        .header(AUTHORIZATION, getAccessToken(member))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @DisplayName("position 순서대로 정렬된 CastFolder 리스트를 조회한다.")
    @Test
    void getFolderDropdownList() throws Exception{
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode folderWithPosition1 = createCastFolder(projectNode, "folder2", 1L);
        CastFolderNode folderWithPosition0 = createCastFolder(projectNode, "folder1", 0L);

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .get(String.format("/v1/cast/folders/%d/dropdown", projectNode.getId()))
                        .header(AUTHORIZATION, getAccessToken(member))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(folderWithPosition0.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(folderWithPosition1.getId()));
    }

    @DisplayName("존재하는 Project Id로 CastFolder 리스트를 조회해야 한다.")
    @Test
    void getFolderDropdownListWithIllegalProjectId() throws Exception{
        // given
        Member member = createMember("member1");
        String expectedErrorCode = ProjectNodeErrorCode.PROJECT_NODE_NOT_FOUND.getCode();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .get(String.format("/v1/cast/folders/%d/dropdown", 0L))
                        .header(AUTHORIZATION, getAccessToken(member))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedErrorCode));
    }

    @DisplayName("CastFolder 정보를 수정한다.")
    @Test
    void updateCastFolderInfo() throws Exception{
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);

        UpdateCastFolderInfo body = new UpdateCastFolderInfo("updated name", "updated description");

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .put(String.format("/v1/cast/folders/%d", savedFolder.getId()))
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Optional<CastFolderNode> updatedFolder = castFolderNodeRepository.findById(savedFolder.getId());
        assertThat(updatedFolder).isPresent();
        assertThat(updatedFolder.get().getId()).isEqualTo(savedFolder.getId());
        assertThat(updatedFolder.get().getName()).isEqualTo(body.name());
        assertThat(updatedFolder.get().getDescription()).isEqualTo(body.description());
    }

    @DisplayName("CastFolder 정보를 수정할 때에는 null이 아닌 이름이 필수입니다.")
    @Test
    void updateCastFolderInfoWithNullName() throws Exception{
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);

        UpdateCastFolderInfo body = new UpdateCastFolderInfo(null, "updated description");
        String expectedErrorCode = GlobalErrorCode.ILLEGAL_ARGUMENT.getCode();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .put(String.format("/v1/cast/folders/%d", savedFolder.getId()))
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedErrorCode))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("CastFolder 이름은 필수입니다."))
        ;
    }

    @DisplayName("CastFolder 정보를 수정할 때에는 blank가 아닌 이름이 필수입니다.")
    @Test
    void updateCastFolderInfoWithBlankName() throws Exception{
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);

        UpdateCastFolderInfo body = new UpdateCastFolderInfo(" ", "updated description");
        String expectedErrorCode = GlobalErrorCode.ILLEGAL_ARGUMENT.getCode();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .put(String.format("/v1/cast/folders/%d", savedFolder.getId()))
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedErrorCode))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("CastFolder 이름은 필수입니다."))
        ;
    }

    private CastFolderNode createCastFolder(ProjectNode projectNode, String folderName, Long position) {
        CastFolderNode castFolderNode = CastFolderNode.builder()
                .name(folderName)
                .position(position)
                .build();
        castFolderNode.connectProject(projectNode);
        return castFolderNodeRepository.save(castFolderNode);
    }

    private ProjectNode createProject(Member savedMember) {
        if (savedMember.getId() == null) {
            throw new IllegalArgumentException("영속화된 멤버가 필요합니다.");
        }
        Project project = Project.builder()
                .projectInfo(new ProjectInfo("test_title", "test_desc", Category.ESSAY, Set.of(Genre.ADVENTURE), null))
                .member(savedMember)
                .build();
        Project savedProject = projectRepository.save(project);

        ProjectNode nodeProject = projectNodeMapper.toNode(savedProject.getId());
        return projectNodeRepository.save(nodeProject);
    }

    private Member createMember(String memberName) {
        Member member = Member.builder()
                .email("test")
                .password("1234")
                .name(memberName)
                .nickname("nickname")
                .phoneNumber("")
                .provider(OauthProvider.GOOGLE)
                .build();
        return memberRepository.save(member);
    }

    private String getAccessToken(Member member) {
        return BEARER_TYPE_SPACE + jwtUtils.generateAccessToken(member);
    }

}
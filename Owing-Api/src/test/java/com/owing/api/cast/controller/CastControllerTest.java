package com.owing.api.cast.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.api.cast.model.dto.request.*;
import com.owing.api.common.util.JwtUtils;
import com.owing.api.project.model.mapper.ProjectNodeMapper;
import com.owing.common.error.code.GlobalErrorCode;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.OauthProvider;
import com.owing.entity.domains.member.repository.MemberRepository;
import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Genre;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.model.ProjectInfo;
import com.owing.entity.domains.project.repository.ProjectRepository;
import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.domains.cast.error.code.CastNodeErrorCode;
import com.owing.node.domains.cast.model.*;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.domains.project.repository.ProjectNodeRepository;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
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
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
//@Transactional("jpaTransactionManager")
@Transactional("neo4jTransactionManager")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@SpringBootTest
class CastControllerTest {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_TYPE_SPACE = "Bearer ";
    private static final String expectedErrorCode = GlobalErrorCode.ILLEGAL_ARGUMENT.getCode();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CastFolderNodeRepository castFolderNodeRepository;
    @Autowired
    private ProjectNodeMapper projectNodeMapper;
    @Autowired
    private ProjectNodeRepository projectNodeRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CastNodeRepository castNodeRepository;
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
        projectRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
//        castNodeRepository.deleteAll();
//        castFolderNodeRepository.deleteAll();
//        projectNodeRepository.deleteAll();
    }

    @DisplayName("cast를 생성한다.")
    @Test
    void createCast() throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);

        CreateCastRequest requestBody = new CreateCastRequest(
                savedFolder.getId(),
                "캐릭터 이름",
                0L,
                "캐릭터 성별",
                "캐릭터 직업, 역할",
                "캐릭터 설명",
                "http://test-image-url/test-primary-key",
                new Coordinate(  // 내부에서 무조건 기본값(0, 0)으로 초기화됨
                        100,
                        100
                )
        );

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/cast")
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(requestBody.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(requestBody.age()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(requestBody.gender()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(requestBody.description()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").value(requestBody.imageUrl()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coordinate.x").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coordinate.y").value(0))
        ;
    }

    @DisplayName("cast 생성시 coordinate를 입력하지 않으면 기본값(0, 0)으로 저장된다.")
    @Test
    void createCastWithoutCoordinate() throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);

        CreateCastRequest requestBody = new CreateCastRequest(
                savedFolder.getId(),
                "캐릭터 이름",
                0L,
                "캐릭터 성별",
                "캐릭터 직업, 역할",
                "캐릭터 설명",
                null,
                null
        );

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/cast")
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(requestBody.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coordinate.x").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coordinate.y").value(0))
        ;
    }

    @DisplayName("cast 생성시 folderId는 필수이다.")
    @Test
    void createCastWithoutFolderId() throws Exception {
        // given
        Member member = createMember("member1");
        CreateCastRequest requestBody = new CreateCastRequest(
                null,
                "캐릭터 이름",
                0L,
                "캐릭터 성별",
                "캐릭터 직업, 역할",
                "캐릭터 설명",
                null,
                null
        );

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/cast")
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedErrorCode))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("소속되는 folderId는 필수입니다."))
        ;
    }

    @DisplayName("cast 생성시 blank가 아닌 이름이 필수이다.")
    @NullSource @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void createCastWithBlankName(String blankCastName) throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);
        CreateCastRequest requestBody = new CreateCastRequest(
                savedFolder.getId(),
                blankCastName,
                0L,
                "캐릭터 성별",
                "캐릭터 직업, 역할",
                "캐릭터 설명",
                null,
                null
        );

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/cast")
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedErrorCode))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("캐릭터의 이름은 필수입니다."))
        ;
    }

    @DisplayName("cast 생성시 age는 0 이상이 필수입니다.")
    @Test
    void createCastWithNegativeAge() throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);
        CreateCastRequest requestBody = new CreateCastRequest(
                savedFolder.getId(),
                "캐릭터 이름",
                -1L,
                "캐릭터 성별",
                "캐릭터 직업, 역할",
                "캐릭터 설명",
                null,
                null
        );


        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/cast")
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedErrorCode))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("캐릭터의 나이는 음수일 수 없습니다."))
        ;
    }

    @DisplayName("cast 정보를 수정한다.")
    @Test
    void updateCastInfo() throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);
        CastNode savedCast = createCast(savedFolder);
        UpdateCastInfoRequest request = new UpdateCastInfoRequest(
                savedFolder.getId(),
                "이름 업데이트",
                0L,
                "성별 업데이트",
                "역할 업데이트",
                "설명 업데이트",
                "http://update-test"
        );

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/cast/{castId}", savedCast.getId())
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent())
        ;

        Optional<CastNode> optionalUpdatedCast = castNodeRepository.findById(savedCast.getId());
        assertThat(optionalUpdatedCast).isPresent();
        CastNode updatedCast = optionalUpdatedCast.get();
        assertThat(updatedCast.getName()).isEqualTo(request.name());
        assertThat(updatedCast.getAge()).isEqualTo(request.age());
        assertThat(updatedCast.getGender()).isEqualTo(request.gender());
        assertThat(updatedCast.getRole()).isEqualTo(request.role());
        assertThat(updatedCast.getDescription()).isEqualTo(request.description());
        assertThat(updatedCast.getImageUrl()).isEqualTo(request.imageUrl());
    }

    @DisplayName("cast 정보를 수정할 때 이름, 나이, 역할은 필수이다.")
    @CsvSource({
            "   ,   1,   역할,    캐릭터의 이름은 필수입니다.",
            "이름,    ,   역할,    캐릭터의 나이는 필수입니다.",
            "이름,   1,      ,    캐릭터의 역할은 필수입니다.",
    })
    @ParameterizedTest
    void updateCastInfoWithoutNameOrAgeOrRole(String name, Long age, String role, String expectedDescription) throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);
        CastNode savedCast = createCast(savedFolder);
        UpdateCastInfoRequest request = new UpdateCastInfoRequest(
                savedFolder.getId(),
                name,
                age,
                "성별 업데이트",
                role,
                "설명 업데이트",
                "http://update-test"
        );

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/cast/{castId}", savedCast.getId())
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedErrorCode))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expectedDescription))
        ;
    }

    @DisplayName("단방향 cast1 -> cast2 관계를 생성한다.")
    @Test
    void createDirectionalRelationship() throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);
        CastNode sourceCast = createCast(savedFolder);
        CastNode targetCast = createCast(savedFolder);

        CreateConnectionRequest request = new CreateConnectionRequest(
                "단방향 관계",
                ConnectionType.DIRECTIONAL,
                sourceCast.getId(),
                ConnectionHandle.TOP,
                targetCast.getId(),
                ConnectionHandle.LEFT
        );

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/cast/relationships")
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.label").value(request.label()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(request.type().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.source").value(request.sourceId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.target").value(request.targetId()))
        ;
        Optional<CastRelationshipProjection> optionalConnection = castNodeRepository.findConnection(sourceCast.getId(), targetCast.getId());
        assertThat(optionalConnection).isPresent();
    }

    @DisplayName("양방향 cast1 <-> cast2 관계를 생성한다.")
    @Test
    void createBiDirectionalRelationship() throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);
        CastNode sourceCast = createCast(savedFolder);
        CastNode targetCast = createCast(savedFolder);

        CreateConnectionRequest request = new CreateConnectionRequest(
                "단방향 관계",
                ConnectionType.BIDIRECTIONAL,
                sourceCast.getId(),
                ConnectionHandle.TOP,
                targetCast.getId(),
                ConnectionHandle.LEFT
        );

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/cast/relationships")
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.label").value(request.label()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(request.type().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.source").value(request.sourceId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.target").value(request.targetId()))
        ;
        Optional<CastRelationshipProjection> optionalConnection = castNodeRepository.findBiconnection(sourceCast.getId(), targetCast.getId());
        assertThat(optionalConnection).isPresent();
    }

    @DisplayName("CreateConnectionRequest의 모든 요소는 필수이다.")
    @CsvSource({
            "          , DIRECTIONAL, 0, top, 1, bottom, 관계 라벨은 필수입니다.",
            "test label,            , 0, top, 1, bottom, 관계 연결 타입은 필수입니다.",
            "test label, DIRECTIONAL,  , top, 1, bottom, 관계의 시작 노드는 필수입니다.",
            "test label, DIRECTIONAL, 0,    , 1, bottom, 시작 노드의 관계 시작 위치는 필수입니다.",
            "test label, DIRECTIONAL, 0, top,  , bottom, 관계의 대상 노드는 필수입니다.",
            "test label, DIRECTIONAL, 0, top, 1,       , 대상 노드의 관계 시작 위치는 필수입니다."
    })
    @ParameterizedTest
    void createRelationshipWithoutNullRequestField(
            String label,
            String typeStr,
            Long sourceId,
            String sourceHandleStr,
            Long targetId,
            String targetHandleStr,
            String expectedDescription
    ) throws Exception {
        // given
        ConnectionType type = typeStr == null ? null : ConnectionType.valueOf(typeStr);
        ConnectionHandle sourceHandle = sourceHandleStr == null ? null : ConnectionHandle.fromString(sourceHandleStr);
        ConnectionHandle targetHandle = targetHandleStr == null ? null : ConnectionHandle.fromString(targetHandleStr);

        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);
        CastNode sourceCast = createCast(savedFolder);
        sourceId = sourceId == null ? null : sourceCast.getId();
        CastNode targetCast = createCast(savedFolder);
        targetId = targetId == null ? null : targetCast.getId();

        CreateConnectionRequest request = new CreateConnectionRequest(
                label,
                type,
                sourceId,
                sourceHandle,
                targetId,
                targetHandle
        );

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/cast/relationships")
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expectedDescription))
        ;
    }

    @DisplayName("updateRelationshipLabel")
    @Test
    void updateRelationshipLabel() throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);
        CastNode sourceCast = createCast(savedFolder);
        CastNode targetCast = createCast(savedFolder);
        CastRelationshipProjection savedRelationship = createRelationship(sourceCast, targetCast, "test label", ConnectionType.DIRECTIONAL);

        UpdateCastRelationshipLabelRequest requestBody = new UpdateCastRelationshipLabelRequest("updated label");

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/v1/cast/relationships/{relationshipId}/label", savedRelationship.relationshipId())
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent())
        ;

        Optional<CastRelationshipProjection> optionalUpdatedRelationship = castNodeRepository.findConnection(sourceCast.getId(), targetCast.getId());
        assertThat(optionalUpdatedRelationship).isPresent();

        CastRelationshipProjection updatedRelationship = optionalUpdatedRelationship.get();
        assertThat(updatedRelationship.relationshipId()).isEqualTo(savedRelationship.relationshipId());
        assertThat(updatedRelationship.sourceId()).isEqualTo(sourceCast.getId());
        assertThat(updatedRelationship.targetId()).isEqualTo(targetCast.getId());
        assertThat(updatedRelationship.label()).isEqualTo(requestBody.label());
    }

    @DisplayName("label 수정 시 수정할 label은 필수이다.")
    @Test
    void updateRelationshipLabelWithoutUpdateLabel() throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);
        CastNode sourceCast = createCast(savedFolder);
        CastNode targetCast = createCast(savedFolder);
        CastRelationshipProjection savedRelationship = createRelationship(sourceCast, targetCast, "test label", ConnectionType.DIRECTIONAL);

        UpdateCastRelationshipLabelRequest requestBody = new UpdateCastRelationshipLabelRequest(null);
        String expectedDescription = "관계의 이름은 필수입니다.";

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/v1/cast/relationships/{relationshipId}/label", savedRelationship.relationshipId())
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedErrorCode))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expectedDescription))
        ;

        Optional<CastRelationshipProjection> optionalResult = castNodeRepository.findConnection(sourceCast.getId(), targetCast.getId());
        assertThat(optionalResult).isPresent();

        CastRelationshipProjection result = optionalResult.get();
        assertThat(result.relationshipId()).isEqualTo(savedRelationship.relationshipId());
        assertThat(result.label()).isEqualTo(savedRelationship.label());
    }

    @DisplayName("관계 정보를 수정할 때 기존의 source & target과 같다면 수정만 진행한다.")
    @Test
    void updateRelationship1() throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);
        CastNode sourceCast = createCast(savedFolder);
        CastNode targetCast = createCast(savedFolder);
        CastRelationshipProjection savedRelationship = createRelationship(sourceCast, targetCast, "test label", ConnectionType.DIRECTIONAL);

        UpdateCastRelationshipRequest requestBody = new UpdateCastRelationshipRequest(
                ConnectionType.BIDIRECTIONAL,
                sourceCast.getId(),
                targetCast.getId(),
                ConnectionHandle.RIGHT,
                ConnectionHandle.LEFT
        );

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/cast/relationships/{relationshipId}", savedRelationship.relationshipId())
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent())
        ;

        Optional<CastRelationshipProjection> optionalRelationship;
        optionalRelationship = castNodeRepository.findConnection(sourceCast.getId(), targetCast.getId());  // 기존 관계 삭제 확인
        assertThat(optionalRelationship).isPresent();

        CastRelationshipProjection updatedRelationship = optionalRelationship.get();
        assertThat(updatedRelationship.relationshipId()).isEqualTo(savedRelationship.relationshipId());
        assertThat(updatedRelationship.sourceHandle()).isEqualTo(requestBody.sourceHandle());
        assertThat(updatedRelationship.targetHandle()).isEqualTo(requestBody.targetHandle());
    }

    @DisplayName("관계 정보를 수정할 때 기존의 source & target과 다르다면 새로운 관계로 교체된다.")
    @Test
    void updateRelationship2() throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);
        CastNode sourceCast = createCast(savedFolder);
        CastNode targetCast = createCast(savedFolder);
        CastRelationshipProjection savedRelationship = createRelationship(sourceCast, targetCast, "test label", ConnectionType.DIRECTIONAL);

        CastNode updatedTargetCast = createCast(savedFolder);
        UpdateCastRelationshipRequest requestBody = new UpdateCastRelationshipRequest(
                ConnectionType.BIDIRECTIONAL,
                sourceCast.getId(),
                updatedTargetCast.getId(),
                ConnectionHandle.RIGHT,
                ConnectionHandle.LEFT
        );

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/cast/relationships/{relationshipId}", savedRelationship.relationshipId())
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source").value(requestBody.sourceId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.target").value(requestBody.targetId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(requestBody.type().name()))
        ;

        Optional<CastRelationshipProjection> optionalRelationship;
        optionalRelationship = castNodeRepository.findConnection(sourceCast.getId(), targetCast.getId());  // 기존 관계 삭제 확인
        assertThat(optionalRelationship).isNotPresent();

        optionalRelationship = castNodeRepository.findBiconnection(sourceCast.getId(), updatedTargetCast.getId());
        assertThat(optionalRelationship).isPresent();

        CastRelationshipProjection updatedRelationship = optionalRelationship.get();
        assertThat(updatedRelationship.relationshipId()).isNotEqualTo(savedRelationship.relationshipId());
        assertThat(updatedRelationship.label()).isEqualTo(savedRelationship.label());
        assertThat(updatedRelationship.sourceHandle()).isEqualTo(requestBody.sourceHandle());
        assertThat(updatedRelationship.targetHandle()).isEqualTo(requestBody.targetHandle());
    }

    private static Stream<Arguments> provideUpdateRelationshipRequestForCheckingValid() {
        return Stream.of(
                Arguments.of(                      null,    1L,    2L, ConnectionHandle.RIGHT, ConnectionHandle.RIGHT, "관계의 타입은 필수입니다."),
                Arguments.of(ConnectionType.DIRECTIONAL,  null,    2L, ConnectionHandle.RIGHT, ConnectionHandle.RIGHT, "관계의 시작 노드 id값은 필수입니다."),
                Arguments.of(ConnectionType.DIRECTIONAL,    1L,  null, ConnectionHandle.RIGHT, ConnectionHandle.RIGHT, "관계의 대상 노드 id값은 필수입니다."),
                Arguments.of(ConnectionType.DIRECTIONAL,    1L,    2L,                   null, ConnectionHandle.RIGHT, "시작 노드의 관계 핸들은 필수입니다."),
                Arguments.of(ConnectionType.DIRECTIONAL,    1L,    2L, ConnectionHandle.RIGHT,                   null, "대상 노드의 관계 핸들은 필수입니다.")
        );
    }
    @DisplayName("관계 정보를 수정할 때 모든 필드는 필수이다.")
    @MethodSource("provideUpdateRelationshipRequestForCheckingValid")
    @ParameterizedTest
    void updateRelationshipWithNullRequestField(
            ConnectionType connectionType,
            Long sourceCastId,
            Long updatedTargetCastId,
            ConnectionHandle sourceHandle,
            ConnectionHandle targetHandle,
            String expectedDescription
    ) throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);
        CastNode sourceCast = createCast(savedFolder);
        CastNode targetCast = createCast(savedFolder);
        CastRelationshipProjection savedRelationship = createRelationship(sourceCast, targetCast, "test label", ConnectionType.DIRECTIONAL);

        UpdateCastRelationshipRequest requestBody = new UpdateCastRelationshipRequest(
                connectionType,
                sourceCastId == null ? null : sourceCast.getId(),  // 인자값이 null이 아닐경우 현재 테스트에서 저장한 cast의 id 사용
                updatedTargetCastId == null ? null : targetCast.getId(),
                sourceHandle,
                targetHandle
        );

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/cast/relationships/{relationshipId}", savedRelationship.relationshipId())
                        .header(AUTHORIZATION, getAccessToken(member))
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedErrorCode))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expectedDescription))
        ;
    }

    @DisplayName("관계를 삭제한다.")
    @Test
    void deleteRelationship() throws Exception {
        // given
        Member member = createMember("member1");
        ProjectNode projectNode = createProject(member);
        CastFolderNode savedFolder = createCastFolder(projectNode, "folder1", 0L);
        CastNode sourceCast = createCast(savedFolder);
        CastNode targetCast = createCast(savedFolder);
        CastRelationshipProjection savedRelationship = createRelationship(sourceCast, targetCast, "test label", ConnectionType.DIRECTIONAL);

        Long targetRelationshipId = savedRelationship.relationshipId();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/cast/relationships/{relationshipId}", targetRelationshipId)
                        .header(AUTHORIZATION, getAccessToken(member))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent())
        ;

        Optional<CastRelationshipProjection> optionalResult = castNodeRepository.findCastRelationshipById(targetRelationshipId);
        assertThat(optionalResult).isNotPresent();
    }

    @DisplayName("관계 삭제 시 존재하는 관계 id는 필수이다.")
    @Test
    void deleteNonExistentRelationship() throws Exception {
        // given
        Member member = createMember("member1");
        Long invalidRelationshipId = 999L; // 존재하지 않는 ID

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/cast/relationships/{relationshipId}", invalidRelationshipId)
                        .header(AUTHORIZATION, getAccessToken(member))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(CastNodeErrorCode.RELATIONSHIP_NOT_FOUND.getCode()))
                ;
    }

    @Test
    @DisplayName("관계 삭제 시 관계 id는 정수 타입이어야 한다.")
    void deleteRelationshipWithInvalidIdFormat() throws Exception {
        // given
        Member member = createMember("member1");

        // when // then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/cast/relationships/{relationshipId}", "invalidId")
                        .header(AUTHORIZATION, getAccessToken(member))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(GlobalErrorCode.ILLEGAL_PATH_ARGS.getCode()))
        ;
    }

    private CastRelationshipProjection createRelationship(CastNode sourceCast, CastNode targetCast, String label, ConnectionType connectionType) {
        CastRelationship relationship = CastRelationship.builder()
                .label(label)
                .sourceId(sourceCast.getId())
                .sourceHandle(ConnectionHandle.TOP)
                .targetId(targetCast.getId())
                .targetHandle(ConnectionHandle.RIGHT)
                .targetNode(targetCast)
                .build();
        castNodeRepository.createCastRelationship(
                relationship.getSourceId(), relationship.getTargetId(),
                connectionType.getValue(), relationship.getLabel(),
                relationship.getSourceHandle().name(), relationship.getTargetHandle().name()
        );
        return castNodeRepository.findConnection(sourceCast.getId(), targetCast.getId())
                .orElseThrow(() -> new RuntimeException("조건을 만족하는 relationship이 없습니다."));
    }

    private CastNode createCast(CastFolderNode castFolderNode) {
        CastNode castNode = CastNode.builder()
                .name("캐릭터 이름")
                .age(100L)
                .gender("캐릭터 성별")
                .role("캐릭터 직업, 역할")
                .description("캐릭터 설명")
                .build();
        castNode.connectFolder(castFolderNode);
        return castNodeRepository.save(castNode);
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
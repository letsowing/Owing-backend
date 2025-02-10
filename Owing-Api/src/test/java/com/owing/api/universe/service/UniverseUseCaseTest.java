package com.owing.api.universe.service;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.api.universe.service.universe.CreateUniverseUseCase;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UniverseUseCaseTest {

    @Mock
    private MemberUtils memberUtils;

    @Mock
    private UniverseDomainService universeDomainService;

    @Mock
    private UniverseFolderAdapter universeFolderAdapter;

    @Mock
    private UniverseMapper universeMapper;

    @InjectMocks
    private CreateUniverseUseCase createUniverseUseCase;

    private Long folderId;
    private AddUniverseRequest addUniverseRequest;
    private UniverseFolder mockFolder;
    private Universe mockUniverse;
    private Universe savedUniverse;
    private UniverseShortInfoResponse universeShortInfoResponse;

    @BeforeEach
    @DisplayName("Mockito Mock 객체 초기화 및 공통 테스트 데이터 설정")
    void setUp() {

        MockitoAnnotations.openMocks(this);

        // 공통 테스트 데이터 초기화
        folderId = 1L;

        addUniverseRequest = new AddUniverseRequest(
                folderId,
                "Test Universe",
                "A test description",
                "http://image.url"
        );

        mockFolder = UniverseFolder.basicBuilder()
                .id(folderId)
                .name("Test Folder")
                .description("Test Description")
                .projectId(100L)
                .build();

        mockUniverse = Universe.builder()
                .name(addUniverseRequest.name())
                .description(addUniverseRequest.description())
                .imageUrl(addUniverseRequest.imageUrl())
                .folder(mockFolder)
                .build();

        savedUniverse = Universe.builder()
                .id(2L)
                .name(addUniverseRequest.name())
                .description(addUniverseRequest.description())
                .imageUrl(addUniverseRequest.imageUrl())
                .folder(mockFolder)
                .build();

        universeShortInfoResponse = new UniverseShortInfoResponse(
                savedUniverse.getId(),
                savedUniverse.getName(),
                savedUniverse.getDescription(),
                savedUniverse.getImageUrl()
        );
    }
}

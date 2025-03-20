package com.owing.api.auth.service;

import com.owing.api.auth.model.dto.response.TokenResponse;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.repository.MemberRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

import static com.google.api.client.json.webtoken.JsonWebToken.Payload;
import static com.owing.common.constant.TokenConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ActiveProfiles("test")
@SpringBootTest
class GoogleOauthLoginUseCaseTest {

    @SpyBean
    private GoogleOauthLoginUseCase useCase;

    @Autowired
    private MemberRepository memberRepository;

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
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("")
    @Test
    void execute() {
        // given

        Payload stubPayload = new Payload()
                .set(GOOGLE_CLAIM_EMAIL, "test@test.com")
                .set(GOOGLE_CLAIM_NAME, "test")
                .set(GOOGLE_CLAIM_NAME, "test")
                .set(GOOGLE_CLAIM_PICTURE, "test-url");
        doReturn(stubPayload)
                .when(useCase)
                .validateGoogleIdToken(anyString());

        // when
        TokenResponse result = useCase.execute("test-id-token");

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertThat(members.getFirst().getEmail()).isEqualTo(stubPayload.get(GOOGLE_CLAIM_EMAIL));
    }
}
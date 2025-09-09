package io.wisoft.javatest.ch6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpClient;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WebsiteCheckerHttpClientV2 단위 테스트")
@ExtendWith(MockitoExtension.class)
class WebsiteCheckerHttpClientV2Test {

    @Mock
    HttpClient mockClient;
    private WebsiteCheckerHttpClientV2 service;

    @Nested
    @DisplayName("processFetchSuccess 메서드는")
    class Describe_processFetchSuccess {

        @Test
        @DisplayName("본문에 'illustrative'가 포함되면 성공(true)으로 콜백을 호출한다")
        void whenContentMatches_callsBackWithSuccess() {
            // Given
            String bodyWithContent = "This response contains the illustrative text.";

            // When & Then
            WebsiteCheckerHttpClientV2.processFetchSuccess(bodyWithContent, (result) -> {
                assertTrue(result.success());
                assertEquals("ok", result.status(), "상태 메시지는 'ok' 이어야 합니다.");
            });
        }

        @DisplayName("본문에 'illustrative'이 없거나 null이면 실패(false)로 콜백을 호출한다.")
        @ParameterizedTest
        @ValueSource(strings = {"some other text", ""})
        @NullSource
        void whenContentDoesNotMatch_callsBackWithFailure(String body) {
            // When & Then
            WebsiteCheckerHttpClientV2.processFetchSuccess(body, (result) -> {
                assertFalse(result.success());
                assertEquals("missing text", result.status(), "상태 메시지는 'missing text' 이어야 합니다.");
            });
        }
    }

    @Nested
    @DisplayName("processFetchError 메서드는")
    class Describe_processFetchError {

        @Test
        @DisplayName("주어진 예외(Exception)의 메시지를 포함하여 실패로 콜백을 호출한다.")
        void whenCalled_callsBackWithExceptionMessage() {
            // Given
            String errorMessage = "Network connection failed";
            var testException = new RuntimeException(errorMessage);

            // When & Then
            WebsiteCheckerHttpClientV2.processFetchError(testException, (result) -> {
                assertFalse(result.success());
                assertEquals(errorMessage, result.status());
            });
        }
    }
}
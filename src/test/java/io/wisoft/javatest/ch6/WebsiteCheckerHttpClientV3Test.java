package io.wisoft.javatest.ch6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("WebsiteCheckerHttpClientV3 단위 테스트")
class WebsiteCheckerHttpClientV3Test {

    @Nested
    @DisplayName("throwOnInvalidResponse 메서드는")
    class Describe_throwOnInvalidResponse {

        @Test
        @DisplayName("HTTP 상태 코드가 200이면, 응답 본문을 반환한다.")
        void whenStatusCodeIs200_itReturnsBody() {
            // Given
            HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(200);
            when(mockResponse.body()).thenReturn("test body");

            // When
            String body = WebsiteCheckerHttpClientV3.validateAndGetBody(mockResponse);

            // Then
            assertEquals("test body", body);
        }

        @Test
        @DisplayName("HTTP 상태 코드가 404이면, RuntimeException을 던진다.")
        void whenStatusCodeIs404_itThrowsRuntimeException() {
            // Given
            HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(404);

            // When & Then
            RuntimeException exception = assertThrows(RuntimeException.class, () ->
                    WebsiteCheckerHttpClientV3.validateAndGetBody(mockResponse));

            assertEquals("HTTP 404", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("processBody 메서드는")
    class Describe_processBody {

        @Test
        @DisplayName("본문에 'illustrative'가 포함되면, 성공 상태를 반환한다.")
        void whenBodyContainsIllustrative_itReturnsSuccessStatus() {
            // Given & When
            WebsiteStatus result = WebsiteCheckerHttpClientV3.processFetchSuccess("This is an illustrative example");

            // Then
            assertTrue(result.success());
            assertEquals("ok", result.status());
        }

        @Test
        @DisplayName("본문에 'illustrative'가 없으면, 실패 상태를 반환한다")
        void whenBodyDoesNotContainIllustrative_itReturnsFailureStatus() {
            // Given & When
            WebsiteStatus result = WebsiteCheckerHttpClientV3.processFetchSuccess("This is an bad example");

            // Then
            assertFalse(result.success());
            assertEquals("missing text", result.status());
        }
    }

    @Nested
    @DisplayName("processError 메서드는")
    class Describe_processError {

        @Test
        @DisplayName("Throwable 객체를 받으면, 실패 상태와 에러 메시지를 반환한다")
        void whenGivenThrowable_itReturnsFailureStatusWithMessage() {
            // Given
            var testException = new RuntimeException("Test Network Error");

            // When
            WebsiteStatus result = WebsiteCheckerHttpClientV3.processFetchError(testException);

            // Then
            assertFalse(result.success());
            assertEquals("Test Network Error", result.status());
        }
    }
}
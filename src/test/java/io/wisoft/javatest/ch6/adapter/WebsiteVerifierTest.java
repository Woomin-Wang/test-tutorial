package io.wisoft.javatest.ch6.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("객체 지향 주입 - 어댑터 분리 패턴 단위 테스트")
class WebsiteVerifierTest {

    @Mock
    private INetworkAdapter mockNetworkAdapter;

    @InjectMocks
    private WebsiteVerifier verifier;

    @Test
    @DisplayName("네트워크 성공 및 본문 일치 시, 성공(true)를 반환한다.")
    void whenNetworkSucceedsAndContentMatches_returnsSuccess() throws Exception {
        // Given
        NetworkResult fakeSuccessResult = new NetworkResult(true, "illustrative");
        when(mockNetworkAdapter.fetchUrlText(anyString()))
                .thenReturn(CompletableFuture.completedFuture(fakeSuccessResult));

        // When
        WebsiteStatus finalResult = verifier.isWebsiteAlive().get();

        // Then
        assertTrue(finalResult.success());
        assertEquals("ok", finalResult.status());
    }

    @Test
    @DisplayName("네트워크 성공했으나 본문 불일치 시, 실패(false)를 반환한다.")
    void whenNetworkSucceedsAndContentMismatches_returnsFailure() throws Exception {
        // Given
        NetworkResult fakeFailureResult = new NetworkResult(false, "missing text");
        when(mockNetworkAdapter.fetchUrlText(anyString()))
                .thenReturn(CompletableFuture.completedFuture(fakeFailureResult));

        // When
        WebsiteStatus finalResult = verifier.isWebsiteAlive().get();

        // Then
        assertFalse(finalResult.success());
        assertEquals("missing text", finalResult.status());
    }

    @Test

    @DisplayName("네트워크 통신 자체가 실패하면, 최공 결과를 실패(false)로 반환한다.")
    void whenNetworkFails_returnFailure() throws Exception {
        // Given
        NetworkResult fakeFailureResult = new NetworkResult(false, "Network Error");
        when(mockNetworkAdapter.fetchUrlText(anyString()))
                .thenReturn(CompletableFuture.completedFuture(fakeFailureResult));

        // When
        WebsiteStatus finalResult = verifier.isWebsiteAlive().get();

        // Then
        assertFalse(finalResult.success());
        assertEquals("Network Error", finalResult.status());
    }
}
package io.wisoft.javatest.ch6.adapter.functional;

import io.wisoft.javatest.ch6.adapter.INetworkAdapter;
import io.wisoft.javatest.ch6.adapter.NetworkResult;
import io.wisoft.javatest.ch6.adapter.WebsiteStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("함수형 주입 어댑터 단위 테스트")
class WebsiteVerifierFunctionalTest {

    private final WebsiteVerifierFunctional verifier = new WebsiteVerifierFunctional();

    @Test
    @DisplayName("네트워크 성공 및 본문 일치 시, 성공을 반환한다.")
    void whenNetworkSucceedsAndContentMatches_returnsSuccess() throws Exception {
        // Given
        INetworkAdapter mockAdapter = mock(INetworkAdapter.class);
        NetworkResult fakeSuccessResult = new NetworkResult(true, "illustrative");
        when(mockAdapter.fetchUrlText(anyString()))
                .thenReturn(CompletableFuture.completedFuture(fakeSuccessResult));

        // When
        WebsiteStatus result = verifier.isWebsiteAlive(mockAdapter).get();

        // Then
        assertTrue(result.success());
        assertEquals("ok", result.status());
    }

    @Test
    @DisplayName("네트워크는 성공했으나 본문이 불일치하면, 실패를 반환한다.")
    void whenNetworkSucceedsButContentMismatches_returnsFailure() throws Exception {
        // Given
        INetworkAdapter mockAdapter = mock(INetworkAdapter.class);
        NetworkResult fakeFailureResult = new NetworkResult(true, "some other content");
        when(mockAdapter.fetchUrlText(anyString()))
                .thenReturn(CompletableFuture.completedFuture(fakeFailureResult));

        // When
        WebsiteStatus result = verifier.isWebsiteAlive(mockAdapter).get();

        // Then
        assertFalse(result.success());
        assertEquals("missing text", result.status());
    }

    @Test
    @DisplayName("네트워크 자체가 실패하면, 실패를 반환한다")
    void whenNetworkFails_returnsFailure() throws Exception {
        // Given: 네트워크 통신 자체가 실패한 상황을 흉내 냅니다.
        INetworkAdapter mockAdapter = mock(INetworkAdapter.class);
        NetworkResult fakeResult = new NetworkResult(false, "Network Error");
        when(mockAdapter.fetchUrlText(anyString()))
                .thenReturn(CompletableFuture.completedFuture(fakeResult));

        // When
        WebsiteStatus result = verifier.isWebsiteAlive(mockAdapter).get();

        // Then
        assertFalse(result.success());
        assertTrue(result.status().contains("Network Error"));
    }
}
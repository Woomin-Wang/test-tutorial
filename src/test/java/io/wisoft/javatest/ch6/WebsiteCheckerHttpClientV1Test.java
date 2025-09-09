package io.wisoft.javatest.ch6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WebsiteCheckerHttpClientV1Test {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final WebsiteCheckerHttpClientV1 service = new WebsiteCheckerHttpClientV1(httpClient);

    @Test
    @DisplayName("콜백 방식: 웹사이트가 정상일 때 성공 상태를 반환한다.")
    void isWebsiteAliveCallBack_WhenWebsiteIsUp_ShouldReturnSuccess_Without_try_Finally() throws InterruptedException {
        // Given
        CountDownLatch latch = new CountDownLatch(1);

        // When & Then
        service.isWebsiteAliveCallBack(result -> {
            assertTrue(result.success());
            assertEquals("ok", result.status());

            latch.countDown();
        });

        boolean completedInTime = latch.await(5, TimeUnit.SECONDS);
        assertTrue(completedInTime, "테스트가 5초 안에 완료되지 않았습니다.");
    }

    @Test
    @DisplayName("콜백 방식: 웹사이트가 정상일 때 성공 상태를 반환한다. (Try-Finally 추가) ")
    void isWebsiteAliveCallBack_WhenWebsiteIsUp_ShouldReturnSuccess_With_try_Finally() throws InterruptedException {
        // Given
        CountDownLatch latch = new CountDownLatch(1);

        // When & Then
        service.isWebsiteAliveCallBack(result -> {
            try {
                assertTrue(result.success());
                assertEquals("ok", result.status());
            } finally {
                latch.countDown();
            }
        });

        boolean completedInTime = latch.await(5, TimeUnit.SECONDS);
        assertTrue(completedInTime, "테스트가 5초 안에 완료되지 않았습니다.");
    }

    @Test
    @DisplayName("보완된 콜백 방식: 검증 실패 시 정확하게 실패한다")
    void isWebsiteAliveCallBack_WhenAssertionFails_ShouldFailCorrectly() throws InterruptedException {
        // Given
        final AtomicReference<Throwable> exceptionContainer = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);

        // When & Then
        service.isWebsiteAliveCallBack(result -> {
            try {
                assertTrue(result.success());
                assertEquals("ok", result.status());
            } catch (Throwable t) {
                exceptionContainer.set(t);
            } finally {
                latch.countDown();
            }
        });

        boolean completedInTime = latch.await(5, TimeUnit.SECONDS);
        assertTrue(completedInTime, "테스트가 5초 안에 완료되지 않았습니다.");

        Throwable capturedException = exceptionContainer.get();
        if (capturedException != null) {
            fail("콜백 내부에서 검증(Assertion)이 실패했습니다.", capturedException);
        }
    }

    @Test
    @DisplayName("Async/Await(join) 방식: 웹사이트가 정상일 때 성공 상태를 반환한다")
    void isWebsiteAliveAsyncAwait_WhenWebsiteIsUp_ShouldReturnSuccess() {
        // Given & When
        WebsiteStatus result = service.isWebsiteAliveAsyncAwait().join();

        // Then
        assertTrue(result.success());
        assertEquals("ok", result.status());
    }
}
package io.wisoft.javatest.ch5;

import io.wisoft.javatest.ch4.PasswordVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;

class PasswordVerifierV2Test {

    @Nested
    @DisplayName("수작업 방식의 가짜 객체 테스트")
    class ManualMockTest {

        @Test
        @DisplayName("호출 추적을 위해 임시 변수와 익명 클래스를 사용하는 번거로운 방식")
        void givenLoggerAndPassingScenario_whenValidated_thenLoggedValueIsPassed() {
            // Given
            final AtomicReference<String> logged = new AtomicReference<>();

            LoggerService manualMockLog = new LoggerService() {
                @Override
                public void info(String text) {
                    logged.set(text);
                }

                @Override
                public void debug(String message) {
                    ;
                }
            };

            PasswordVerifierV2 passwordVerifier = new PasswordVerifierV2(manualMockLog);

            // When
            passwordVerifier.verify("any-password");

            // Then
            assertTrue(logged.get().contains("PASSED"));
        }
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    @DisplayName("Mockito 프레임워크 방식의 가짜 객체 테스트")
    class FrameworkMockTest {

        @Mock
        private LoggerService mockLog;

        @Test
        @DisplayName("검증 통과 시, logger.info가 'PASSED'를 포함한 문자열로 호출되는지 검증한다")
        void givenPassingScenario_whenValidated_thenLoggerInfoIsCalled() {
            // Given (준비)
            PasswordVerifierV2 passwordVerifier = new PasswordVerifierV2(mockLog);

            // When
            passwordVerifier.verify("any-password");

            // Then
            verify(mockLog).info(contains("PASS"));
        }
    }
}
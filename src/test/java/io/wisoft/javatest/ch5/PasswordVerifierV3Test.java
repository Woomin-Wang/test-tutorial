package io.wisoft.javatest.ch5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class PasswordVerifierV3Test {

    @Nested
    @DisplayName("복잡한 인터페이스를 수작업으로 테스트하기")
    class ManualComplexMockTest {

        static class FakeLogger implements IComplicatedLogger {
            String infoText = "";
            String infoMethod = "";

            @Override
            public void info(String text, String method) {
                this.infoText = text;
                this.infoMethod = method;
            }

            @Override
            public void debug(String text, String method) {
            }

            @Override
            public void warn(String text, String method) {
            }

            @Override
            public void error(String text, String method) {
            }
        }

        @Test
        void verify_withManualLoggerAndPassing_callsLoggerWithPASS() {
            // Given
            FakeLogger fakeLogger = new FakeLogger();
            PasswordVerifierV3 verifier = new PasswordVerifierV3(new ArrayList<>(), fakeLogger);

            // When
            verifier.verify("any-password");

            // Then
            assertTrue(fakeLogger.infoText.contains("PASSED"));
        }
    }

    @ExtendWith(MockitoExtension.class)
    @Nested
    @DisplayName("Mockito로 복잡한 인터페이스 간결하게 테스트하기")
    class FrameworkComplexMockTest {

        @Mock
        private IComplicatedLogger mockLogger;

        @Test
        @DisplayName("통과 시, logger.info가 올바른 인자들로 호출되는지 검증한다")
        void verify_withMockLoggerAndPassing_callsLoggerWithPASS() {
            // Given
            PasswordVerifierV3 verifier = new PasswordVerifierV3(new ArrayList<>(), mockLogger);

            // When
            verifier.verify("any-password");

            // Then
            verify(mockLogger).info("PASSED", "verify");

//            verify(mockLogger).info(
//                    contains("PASS"),
//                    eq("verify")
//            );

//            verify(mockLogger).info("PASSED", contains("verify"));    // 예외 발생
        }
    }
}








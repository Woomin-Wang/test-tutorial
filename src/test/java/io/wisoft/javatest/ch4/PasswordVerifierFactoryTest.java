package io.wisoft.javatest.ch4;

import io.wisoft.javatest.ch4.external.ILogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("커링 스타일 패스워드 검증기")
class PasswordVerifierFactoryTest {

    @Nested
    @DisplayName("행위 검증")
    @ExtendWith(MockitoExtension.class)
    class BehaviorVerification {

        @Mock
        ILogger mockLogger;

        @Test
        @DisplayName("검증 성공 시, 로거에 'PASSED'를 기록한다")
        void should_LogPass_when_VerificationSucceeds() {
            // Given
            Predicate<String> verifier = PasswordVerifierFactory.verifyPassword(new ArrayList<>(), mockLogger);

            // When
            boolean result = verifier.test("anything");

            // Then
            assertTrue(result, "성공 시에는 true를 반환해야 합니다.");
            verify(mockLogger, times(1)).info("PASSED");
            verify(mockLogger, never()).info("FAIL");
        }


        @Test
        @DisplayName("검증 실패 시, 로거에 'FAIL'을 기록한다")
        void should_LogFail_when_VerificationFails() {
            // Given
            List<Predicate<String>> failingRules = List.of(password -> false);
            Predicate<String> verifier = PasswordVerifierFactory.verifyPassword(failingRules, mockLogger);

            // When
            boolean result = verifier.test("anything");

            // Then
            assertFalse(result, "실패 시에는 false를 반환해야 합니다.");
            verify(mockLogger, times(1)).info("FAIL");
            verify(mockLogger, never()).info("PASSED");
        }
    }

    @Nested
    @DisplayName("상태 검증")
    class StateVerification {

        @Test
        @DisplayName("검증 성공 시, 로거의 상태가 'PASSED'로 변경된다")
        void should_ChangeLoggerStateToPassed_when_VerificationSucceeds() {
            // Given
            final String[] logger = {""};
            ILogger mockLog = new ILogger() {
                @Override
                public void info(String text) {
                    logger[0] = text;
                }

                @Override
                public void debug(String text) {
                }
            };

            // When
            Predicate<String> injectedVerify = PasswordVerifierFactory.verifyPassword(new ArrayList<>(), mockLog);
            injectedVerify.test("anything");

            // Then
            assertEquals("PASSED", logger[0]);
        }
    }
}
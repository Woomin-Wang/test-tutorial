package io.wisoft.javatest.ch4;

import io.wisoft.javatest.ch4.external.RealLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("객체 지향 방식의 부분 모의 객체(상속) 테스트")
class PasswordVerifierPartialMockInheritanceTest {

    private static class TestableLogger extends RealLogger {

        private String loggedText = "";

        @Override
        public void info(String text) {
            this.loggedText = text;
        }

        public String getLoggedText() {
            return loggedText;
        }
    }

    @Test
    @DisplayName("verify 호출 시, logger.info가 호출되어야 한다")
    void verify_withLogger_callsLogger() {
        // Given
        TestableLogger testableLogger = new TestableLogger();
        PasswordVerifierPartialMock verifier = new PasswordVerifierPartialMock(new ArrayList<>(), testableLogger);

        // When
        verifier.verifyPassword("any input");

        // Then
        assertThat(testableLogger.getLoggedText()).contains("PASSED");
    }
}
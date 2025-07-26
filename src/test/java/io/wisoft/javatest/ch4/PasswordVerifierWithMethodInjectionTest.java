package io.wisoft.javatest.ch4;

import io.wisoft.javatest.ch4.external.ILogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("password verifier with logger")
class PasswordVerifierWithMethodInjectionTest {

    PasswordVerifierWithMethodInjection verifier;

    @BeforeEach
    void setUp() {
        verifier = new PasswordVerifierWithMethodInjection();
    }

    @Nested
    @DisplayName("when all rules pass")
    class WhenAllRulesPass {

        @Test
        @DisplayName("calls the logger with PASSED")
        void callsTheLoggerWithPassed() {
            // Given
            final String[] written = {""};

            ILogger mockLog = new ILogger() {

                @Override
                public void info(String text) {
                    written[0] = text;

                }

                @Override
                public void debug(String text) {
                }
            };

            // When
            verifier.verifyPassword("anything", new ArrayList<>(), mockLog);

            // Then
            assertEquals("PASSED", written[0]);
        }
    }
}

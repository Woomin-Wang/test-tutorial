package io.wisoft.javatest.ch4;

import io.wisoft.javatest.ch4.external.FakeLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("생성자 주입을 사용하는 PasswordVerifier")
class PasswordVerifierWithConstructorInjectionTest {

    @Test
    @DisplayName("모든 규칙 통과 시, true를 반환하고 'PASSED'를 로그한다")
    void verify_givenPassingRules_returnsTrue_and_logsPASSED() {
        // Given
        FakeLogger fakeLogger = new FakeLogger();
        PasswordVerifierWithConstructorInjection verifier = new PasswordVerifierWithConstructorInjection(new ArrayList<>(), fakeLogger);

        // When
        boolean result = verifier.verifyPassword("anything");

        // Then
        assertTrue(result, "성공 시에는 true를 반환해야 합니다.");
        assertEquals("PASSED", fakeLogger.infoWritten);
    }


    @Test
    @DisplayName("하나 이상의 규칙 실패 시, false를 반환하고 'FAIL'을 로그한다")
    void verify_givenFailingRules_returnsFalse_and_logsFAIL() {
        // Given
        FakeLogger fakeLogger = new FakeLogger();
        List<Predicate<String>> failingRules = List.of(password -> false);
        PasswordVerifierWithConstructorInjection verifier = new PasswordVerifierWithConstructorInjection(failingRules, fakeLogger);

        // When
        boolean result = verifier.verifyPassword("anything");

        // Then
        assertFalse(result, "실패 시에는 false를 반환해야 합니다.");
        assertEquals("FAIL", fakeLogger.infoWritten);
    }
}
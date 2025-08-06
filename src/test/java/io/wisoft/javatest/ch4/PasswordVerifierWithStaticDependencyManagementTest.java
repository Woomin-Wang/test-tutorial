package io.wisoft.javatest.ch4;

import io.wisoft.javatest.ch4.external.ConfigurationService;
import io.wisoft.javatest.ch4.external.ILogger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static io.wisoft.javatest.ch4.PasswordVerifierWithStaticDependencyManagement.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("정적 의존성 관리를 사용하는 패스워드 검증기")
class PasswordVerifierWithStaticDependencyManagementTest {

    @Mock
    private ILogger mockLogger;

    @Mock
    ConfigurationService mockConfigService;

    @AfterEach
    void cleanUp() {
        resetDependencies();
    }

    @Test
    @DisplayName("규칙이 통과되면 PASSED를 로그하고 true를 반환")
    void calls_logger_with_PASSED_when_rules_pass() {
        // Given
        when(mockConfigService.getLogLevel()).thenReturn("info");
        Dependencies fakeDependencies = new Dependencies(mockLogger, mockConfigService);
        injectDependencies(fakeDependencies);
        PasswordVerifierWithStaticDependencyManagement verifier = new PasswordVerifierWithStaticDependencyManagement();

        // When
        boolean result = verifier.verifyPassword("anything", new ArrayList<>());

        // Then
        assertTrue(result);
        verify(mockLogger).info("PASSED");
    }

    @Test
    @DisplayName("규칙이 실패하면 FAIL을 로그하고 false를 반환")
    void calls_logger_with_FAIL_when_rules_fail() {
        // Given
        when(mockConfigService.getLogLevel()).thenReturn("info");
        Dependencies fakeDependencies = new Dependencies(mockLogger, mockConfigService);
        injectDependencies(fakeDependencies);
        PasswordVerifierWithStaticDependencyManagement verifier = new PasswordVerifierWithStaticDependencyManagement();
        List<Predicate<String>> rules = new ArrayList<>();
        rules.add(s -> false);

        // When
        boolean result = verifier.verifyPassword("anything", rules);

        // Then
        assertFalse(result);
        verify(mockLogger).info("FAIL");
    }
}

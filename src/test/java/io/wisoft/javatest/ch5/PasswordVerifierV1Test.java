package io.wisoft.javatest.ch5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Predicate;

import static org.mockito.Mockito.*;

@DisplayName("PasswordVerifier 단위 테스트")
@ExtendWith(MockitoExtension.class)
class PasswordVerifierV1Test {

    @Mock
    private LoggerService mockLogger;

    @Mock
    private ConfigurationService mockConfig;

    @InjectMocks
    private PasswordVerifierV1 passwordVerifier;


    @Nested
    @DisplayName("verify 메서드는")
    class Describe_verify {

        @Nested
        @DisplayName("모든 검증 규칙을 통과했을 때")
        class Context_when_all_rules_pass {

            private final Predicate<String> passingRule = (password) -> true;

            @Test
            @DisplayName("INFO 로그 레벨에서 'PASSED'를 기록한다.")
            void if_logs_PASSED_with_info_level() {
                // Given
                when(mockConfig.getLogLevel()).thenReturn("info");

                // When
                passwordVerifier.verify("any-password", List.of(passingRule));

                // Then
                verify(mockLogger, times(1)).info("PASSED");
                verify(mockLogger, never()).debug(anyString());
            }


            @Test
            @DisplayName("DEBUG 로그 레벨에서 'PASSED'를 기록한다.")
            void if_logs_PASSED_with_debug_level() {
                // Given
                when(mockConfig.getLogLevel()).thenReturn("debug");

                // When
                passwordVerifier.verify("any-password", List.of(passingRule));

                // Then
                verify(mockLogger, times(1)).debug("PASSED");
                verify(mockLogger, never()).info(anyString());
            }
        }

        @Nested
        @DisplayName("검증 규칙 중 하나라도 실패했을 때")
        class Context_when_a_rule_fails {

            private final Predicate<String> failingRule = (password) -> false;

            @Test
            @DisplayName("INFO 로그 레벨에서 'FAIL'을 기록한다")
            void if_logs_FAILD_with_info_level() {
                // Given
                when(mockConfig.getLogLevel()).thenReturn("info");

                // When
                passwordVerifier.verify("any-password", List.of(failingRule));

                // Then
                verify(mockLogger, times(1)).info("FAIL");
                verify(mockLogger, never()).debug(anyString());
            }
        }
    }
}
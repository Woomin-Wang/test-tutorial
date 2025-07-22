package io.wisoft.javatest.ch3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PasswordVerifierByMethodArgTest {

    private PasswordVerifierByMethodArg verifier;

    @BeforeEach
    void setUp() {
        verifier = new PasswordVerifierByMethodArg();
    }

    @Test
    @DisplayName("평일에는 예외를 던지지 않아야 합니다.")
    void verify_onWeekday_doesNotThrowException() {
        // Given
        LocalDate aMonday = LocalDate.of(2025, 7, 28);

        // When
        List<String> errors = verifier.verifyPassword("anything", aMonday);

        // Then
        assertNotNull(errors);
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("주말에는 예외를 던져야 합니다.")
    void verify_onWeekend_throwsException() {
        // Given
        LocalDate aSunday = LocalDate.of(2025, 7, 27);

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> verifier.verifyPassword("anything", aSunday));

        assertThat(exception.getMessage()).isEqualTo("It's the weekend!");
    }
}

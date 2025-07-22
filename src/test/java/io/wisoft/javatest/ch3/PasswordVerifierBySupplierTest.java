package io.wisoft.javatest.ch3;

import io.micrometer.observation.Observation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordVerifierBySupplierTest {

    private PasswordVerifierBySupplier verifier;

    @BeforeEach
    void setUp() {
         verifier = new PasswordVerifierBySupplier();
    }

    @Test
    @DisplayName("월요일 스텁으로 검증 시 예외를 던지지 않아야 합니다.")
    void verify_withMondayStub_doesNotThrowException() {
        // Given
        Supplier<DayOfWeek> alwaysMonday = () -> DayOfWeek.MONDAY;

        // When
        List<String> errors = verifier.verifyPassword("anything", alwaysMonday);

        // Then
        assertNotNull(errors);
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("일요일 스텁으로 검증 시 예외를 던져야 합니다.")
    void verify_withSundayStub_throwsException() {
        // Given
        Supplier<DayOfWeek> alwaysSunday = () -> DayOfWeek.SUNDAY;

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> verifier.verifyPassword("anything", alwaysSunday));

        assertThat(exception.getMessage()).isEqualTo("It's the weekend!");
    }
}
package io.wisoft.javatest.ch3;

import io.micrometer.observation.Observation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordVerifierBySupplierTest {

    private final PasswordVerifierBySupplier verifier = new PasswordVerifierBySupplier();
    
    @Test
    @DisplayName("월요일 스텁으로 검증 시 예외를 던지지 않아야 합니다.")
    void verify_withMondayStub_doesNotThrowException() {
        // Given
        Supplier<DayOfWeek> alwaysMonday = () -> DayOfWeek.MONDAY;

        // When & Then
        assertDoesNotThrow(() ->
                verifier.verifyPassword("anything", alwaysMonday));
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
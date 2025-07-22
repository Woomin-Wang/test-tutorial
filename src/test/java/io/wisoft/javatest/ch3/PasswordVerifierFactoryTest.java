package io.wisoft.javatest.ch3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PasswordVerifierFactoryTest {

    @Test
    @DisplayName("월요일 스텁으로 검증 시 예외를 던지지 않아야 합니다.")
    void verify_withMondayStub_doesNotThrowException() {
        // Given
        Supplier<DayOfWeek> alwaysMonday = () -> DayOfWeek.MONDAY;
        Function<String, List<String>> verifier = PasswordVerifierFactory.makeVerifier(new ArrayList<>(), alwaysMonday);

        // When
        List<String> errors = verifier.apply("anything");

        // Then
        assertNotNull(errors);
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("일요일 스텁으로 검증 시 예외를 던져야 합니다.")
    void verify_withSundayStub_throwsException() {
        // Given
        Supplier<DayOfWeek> alwaysSunday = () -> DayOfWeek.SUNDAY;
        Function<String, List<String>> verifier = PasswordVerifierFactory.makeVerifier(new ArrayList<>(), alwaysSunday);

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> verifier.apply("anything"));

        assertThat(exception.getMessage()).isEqualTo("It's the weekend!");
    }
}
package io.wisoft.javatest.ch3;

import io.wisoft.javatest.ch2.PasswordValidationRule;
import io.wisoft.javatest.ch2.PasswordVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PasswordVerifierByConstructorTest {

    @Test
    @DisplayName("평일에는 에러가 발생하지 않아야 합니다.")
    void verify_onWeekday_returnsNoErrors() {
        // Given
        Supplier<DayOfWeek> alwaysMonday = () -> DayOfWeek.MONDAY;
        PasswordVerifierByConstructor verifier = makeVerifier(new ArrayList<>(), alwaysMonday);

        // When
        List<String> errors = verifier.verifyPassword("anything");

        // Then
        assertNotNull(errors);
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("주말에는 예외가 발생해야 합니다.")
    void verify_onWeekend_throwsException() {
        // Given
        Supplier<DayOfWeek> alwaysSunday = () -> DayOfWeek.SUNDAY;
        PasswordVerifierByConstructor verifier = makeVerifier(new ArrayList<>(), alwaysSunday);

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> verifier.verifyPassword("anything"));

        assertEquals("It's the weekend!", exception.getMessage(), "Exception message should match");
    }

    private PasswordVerifierByConstructor makeVerifier(List<PasswordValidationRule> rules, Supplier<DayOfWeek> dayOfWeekSupplier) {
        return new PasswordVerifierByConstructor(rules, dayOfWeekSupplier);
    }
}
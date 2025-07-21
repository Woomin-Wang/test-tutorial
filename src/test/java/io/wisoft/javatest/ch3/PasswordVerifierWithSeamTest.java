package io.wisoft.javatest.ch3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordVerifierWithSeamTest {

    private void injectFakeDate(DayOfWeek day) {
        PasswordVerifierWithSeam.Dependencies fakes = new PasswordVerifierWithSeam.Dependencies();
        fakes.dayOfWeekSupplier = () -> day;
        PasswordVerifierWithSeam.inject(fakes);
    }

    @Test
    @DisplayName("심(Seam) 패턴: 주말일 때 예외를 던져야 한다")
    void verify_onWeekend_throwsException() {
        try {
            injectFakeDate(DayOfWeek.SATURDAY);

            PasswordVerifierWithSeam verifier = new PasswordVerifierWithSeam();

            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> verifier.verifyPassword("any input")
            );

            assertEquals("It's the weekend!", exception.getMessage());

        } finally {
            PasswordVerifierWithSeam.reset();
        }
    }
}
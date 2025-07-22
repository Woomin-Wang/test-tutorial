package io.wisoft.javatest.ch3;

import io.wisoft.javatest.ch3.adapters.FakeTimeProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PasswordVerifierWithTimeProviderTest {

    @Test
    @DisplayName("손수 만든 스텁 객체: 주말에는 예외를 던져야 한다.")
    void verify_onWeekend_throwsException() {
        // Given
        FakeTimeProvider faceTimeProvider = new FakeTimeProvider(DayOfWeek.SUNDAY);
        PasswordVerifierWithTimeProvider verifier = new PasswordVerifierWithTimeProvider(new ArrayList<>(), faceTimeProvider);

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> verifier.verifyPassword("anything"));

        assertEquals("It's the weekend!", exception.getMessage());
    }
}
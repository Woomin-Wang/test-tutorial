package io.wisoft.javatest.ch3;

import io.wisoft.javatest.ch3.time.FakeTimeProvider;
import io.wisoft.javatest.ch3.time.RealTimeProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PasswordVerifierWithTimeProviderTest {

    @Test
    @DisplayName("직접 만든 스텁 객체: 평일이고 추가 규칙이 없을 때, 에러 없이 통과해야 한다.")
    void verify_onWeekDay_returnNoErrors() {
        // Given
        FakeTimeProvider fakeTimeProvider = new FakeTimeProvider(DayOfWeek.MONDAY);
        PasswordVerifierWithTimeProvider verifier = new PasswordVerifierWithTimeProvider(new ArrayList<>(), fakeTimeProvider);

        // When
        List<String> result = verifier.verifyPassword("anything");

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("직접 만든 스텁 객체: 주말에는 예외를 던져야 한다.")
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
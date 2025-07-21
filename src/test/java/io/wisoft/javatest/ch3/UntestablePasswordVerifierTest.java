package io.wisoft.javatest.ch3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
class UntestablePasswordVerifierTest {

    private final UntestablePasswordVerifier verifier = new UntestablePasswordVerifier();
    
    @Test
    @DisplayName("주말에는 예외를 던져야 한다")
    void verify_onWeekends_throwsException() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        if(today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY) {
            IllegalStateException exception = assertThrows(IllegalStateException.class,
                    () -> verifier.verifyPassword("anything")
            );
            assertThat(exception.getMessage()).isEqualTo("It's the weekend!");
        }
    }
    
    @Test
    @EnabledIf("isWeekend")
    @DisplayName("주말에는 예외를 던져야 한다 (EnabledIf 버전)")
    void verify_onWeekends_throwsAnError_enabledIf() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> verifier.verifyPassword("anything")
        );
        assertThat(exception.getMessage()).isEqualTo("It's the weekend!");
    }

    static boolean isWeekend() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        return today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY;
    }
}
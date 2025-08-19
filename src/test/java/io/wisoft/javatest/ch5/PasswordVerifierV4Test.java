package io.wisoft.javatest.ch5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordVerifierV4Test {

    @Mock
    private IComplicatedLogger mockLogger;

    @Mock
    private MaintenanceWindow stubMainWindow;

    private PasswordVerifierV4 makeVerifier() {
        return new PasswordVerifierV4(
                new ArrayList<>(),
                mockLogger,
                stubMainWindow
        );
    }
    
    @Test
    @DisplayName("유지보수 기간 중에는 'Under Maintenance'를 로깅한다.")
    void verify_duringMaintenance_callsLogger() {
        // Given
        when(stubMainWindow.isUnderMaintenance()).thenReturn(true);
        PasswordVerifierV4 verifier = makeVerifier();

        // When
        verifier.verify("any-password");
    
        // Then
        verify(mockLogger).info("Under Maintenance", "verify");
    }
    
    @Test
    @DisplayName("유지보수 기간이 아닐 때는 'PASSED'를 로깅한다")
    void verify_outsideMaintenance_callsLogger() {
        // Given
        when(stubMainWindow.isUnderMaintenance()).thenReturn(false);
        PasswordVerifierV4 verifier = makeVerifier();

        // When
        verifier.verify("any-password");

        // Then
        verify(mockLogger).info("PASSED", "verify");
    }

    @Nested
    @DisplayName("Mockito로 동적 스텁 설정하기")
    class DynamicStubbingTest {
        
        @Test
        @DisplayName("호출할 때마다 항상 동일한 값을 반환하도록 설정 (thenReturn)")
        void fakeSameReturnValues() {
            // Given
            Supplier<String> stubFunc = mock(Supplier.class);
            when(stubFunc.get()).thenReturn("abc");

            // When & Then
            assertEquals("abc", stubFunc.get());
            assertEquals("abc", stubFunc.get());
            assertEquals("abc", stubFunc.get());                       
        }
        
        @Test
        @DisplayName("예외를 발생시키도록 설정 (thenThrow)")
        void fakeExceptionThrowing() {
            // Given
            Supplier<String> stubFunc = mock(Supplier.class);
            when(stubFunc.get()).thenThrow(new RuntimeException("Test Error"));

            // When & Then
            assertThrows(RuntimeException.class, () -> stubFunc.get());
        }
    }
}





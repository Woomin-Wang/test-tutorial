package io.wisoft.javatest.ch4;

import io.wisoft.javatest.ch4.external.RealLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@DisplayName("함수형 방식의 부분 모의 객체(Spy) 테스트")
class PasswordVerifierPartialMockTest {

    @Test
    @DisplayName("verify 메서드 호출 시, logger의 info를 호출해야 한다.")
    void verify_withLogger_callsLogger() {
        // Given
        RealLogger realLogger = new RealLogger();
        RealLogger spyLogger = Mockito.spy(realLogger);

        final AtomicReference<String> logged = new AtomicReference<>();

        doAnswer(invocation -> {
            logged.set(invocation.getArgument(0));
            return null;
        }).when(spyLogger).info(anyString());

        PasswordVerifierPartialMock verifier = new PasswordVerifierPartialMock(new ArrayList<>(), spyLogger);

        // When
        verifier.verifyPassword("any input");

        // Then
        assertThat(logged.get()).contains("PASSED");
    }
}
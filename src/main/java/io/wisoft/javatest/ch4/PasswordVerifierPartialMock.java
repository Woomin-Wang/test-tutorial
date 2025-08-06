package io.wisoft.javatest.ch4;

import io.wisoft.javatest.ch4.external.ILogger;
import io.wisoft.javatest.ch4.external.RealLogger;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class PasswordVerifierPartialMock {

    private final List<Object> rules;
    private final RealLogger logger;

    public PasswordVerifierPartialMock(List<Object> rules, RealLogger logger) {
        this.rules = rules;
        this.logger = logger;
    }

    public void verifyPassword(String input) {
        // ... 검증 로직 수행 ...

        // 검증이 끝나면 logger의 info 메서드를 호출한다.
        this.logger.info("verify PASSED for input: " + input);
    }
}

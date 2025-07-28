package io.wisoft.javatest.ch4;

import io.wisoft.javatest.ch4.external.IComplicatedLogger;
import io.wisoft.javatest.ch4.external.ILogger;

import java.util.List;
import java.util.function.Predicate;

public class PasswordVerifierWithComplicatedLogger {
    private final List<Predicate<String>> rules;
    private final IComplicatedLogger logger;

    public PasswordVerifierWithComplicatedLogger(List<Predicate<String>> rules, IComplicatedLogger logger) {
        this.rules = rules;
        this.logger = logger;
    }

    public boolean verifyPassword(String input) {
        long failedCount = this.rules.stream()
                .filter(rule -> !rule.test(input))
                .count();

        if(failedCount == 0) {
            this.logger.info("PASSED");
            return true;
        }
        this.logger.info("FAIL");
        return false;
    }
}

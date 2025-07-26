package io.wisoft.javatest.ch4;

import io.wisoft.javatest.ch4.external.ILogger;

import java.util.List;
import java.util.function.Predicate;

public class PasswordVerifierWithMethodInjection {

    public boolean verifyPassword(String input, List<Predicate<String>> rules, ILogger logger) {
        long failedCount = rules.stream()
                .filter(rule -> !rule.test(input))
                .count();

        if (failedCount == 0) {
            logger.info("PASSED");
            return true;
        }
        logger.info("FAIL");
        return false;
    }
}

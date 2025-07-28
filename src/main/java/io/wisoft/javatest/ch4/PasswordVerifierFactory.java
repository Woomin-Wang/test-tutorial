package io.wisoft.javatest.ch4;

import io.wisoft.javatest.ch4.external.ILogger;

import java.util.List;
import java.util.function.Predicate;

public class PasswordVerifierFactory {

    public static Predicate<String> verifyPassword(List<Predicate<String>> rules, ILogger logger) {
        return (input) -> {
            long failedCount = rules.stream()
                    .filter(rule -> !rule.test(input))
                    .count();

            if(failedCount == 0) {
                logger.info("PASSED");
                return true;
            }
            logger.info("FAIL");
            return false;
        };
    }
}

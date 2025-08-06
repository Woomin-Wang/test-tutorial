package io.wisoft.javatest.ch4;

import io.wisoft.javatest.ch4.external.ComplicatedLogger;

import java.util.List;
import java.util.function.Predicate;

public class PasswordVerifier {

    private static final ComplicatedLogger logger = new ComplicatedLogger();

    public boolean verifyPassword(String input, List<Predicate<String>> rules) {
        long failCount = rules.stream()
                .filter(rule -> !rule.test(input))
                .count();

        if (failCount == 0) {
            logger.info("PASSED");
            return true;
        }

        logger.info("FAIL");
        return false;
    }
}
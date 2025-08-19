package io.wisoft.javatest.ch5;

import java.util.List;
import java.util.function.Predicate;

public class PasswordVerifierV3 {
    private final List<Predicate<String>> rules;
    private final IComplicatedLogger logger;

    public PasswordVerifierV3(List<Predicate<String>> rules, IComplicatedLogger logger) {
        this.rules = rules;
        this.logger = logger;
    }

    public boolean verify(String input) {
        long failedCount = this.rules.stream()
                .filter(rule -> !rule.test(input))
                .count();

        if(failedCount == 0 ) {
            this.logger.info("PASSED", "verify");
            return true;
        }
        this.logger.info("FAIL", "verify");
        return false;
    }
}



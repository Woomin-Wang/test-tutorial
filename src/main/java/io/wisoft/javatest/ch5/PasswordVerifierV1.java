package io.wisoft.javatest.ch5;

import java.util.List;
import java.util.function.Predicate;

public class PasswordVerifierV1 {

    private final LoggerService logger;
    private final ConfigurationService config;

    public PasswordVerifierV1(LoggerService logger, ConfigurationService config) {
        this.logger = logger;
        this.config = config;
    }

    private void log(String text) {
        String logLevel = config.getLogLevel();
        if("info".equals(logLevel)) {
            logger.info(text);
        }

        if("debug".equals(logLevel)) {
            logger.debug(text);
        }
    }

    public boolean verify(String input, List<Predicate<String>> rules) {
        long failedCount = rules.stream()
                .filter(rule -> !rule.test(input))
                .count();

        if (failedCount == 0) {
            log("PASSED");
            return true;
        }
        log("FAIL");
        return false;
    }
}

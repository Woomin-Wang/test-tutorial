package io.wisoft.javatest.ch4;

import io.wisoft.javatest.ch4.external.ComplicatedLogger;
import io.wisoft.javatest.ch4.external.ConfigurationService;
import io.wisoft.javatest.ch4.external.ILogger;

import java.util.List;
import java.util.function.Predicate;

public class PasswordVerifierWithHardcodedDependencies {

    private final ILogger logger = new ComplicatedLogger();
    private final ConfigurationService configService = new ConfigurationService();

    private void log(String text) {
        if ("info".equals(configService.getLogLevel())) {
            logger.info(text);
        }
        if ("debug".equals(configService.getLogLevel())) {
            logger.debug(text);
        }
    }

    public boolean verifyPassword(String input, List<Predicate<String>> rules) {
        long failedCount = rules.stream()
                .filter(rule -> !(rule.test(input)))
                .count();

        if (failedCount == 0) {
            log("PASSED");
            return true;
        }
        log("FAIL");
        return false;
    }
}

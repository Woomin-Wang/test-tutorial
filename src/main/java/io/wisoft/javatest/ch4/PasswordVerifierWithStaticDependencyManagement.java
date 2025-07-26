package io.wisoft.javatest.ch4;

import io.wisoft.javatest.ch4.external.ComplicatedLogger;
import io.wisoft.javatest.ch4.external.ConfigurationService;
import io.wisoft.javatest.ch4.external.ILogger;

import java.util.List;
import java.util.function.Predicate;

public class PasswordVerifierWithStaticDependencyManagement {

    public static class Dependencies {
        private final ILogger logger;
        private final ConfigurationService configService;

        public Dependencies(ILogger logger, ConfigurationService configService) {
            this.logger = logger;
            this.configService = configService;
        }

        public ILogger getLogger() {
            return logger;
        }

        public ConfigurationService getConfigService() {
            return configService;
        }
    }

    private static final Dependencies ORIGINAL_DEPENDENCIES = new Dependencies(
            new ComplicatedLogger(),
            new ConfigurationService()
    );

    private static Dependencies dependencies = ORIGINAL_DEPENDENCIES;

    public static void resetDependencies() {
        dependencies = ORIGINAL_DEPENDENCIES;
    }

    public static void injectDependencies(Dependencies fakes) {
        dependencies = fakes;
    }

    private static void log(String text) {
        if ("info".equals(dependencies.getConfigService().getLogLevel())) {
            dependencies.getLogger().info(text);
        }
        if ("debug".equals(dependencies.getConfigService().getLogLevel())) {
            dependencies.getLogger().debug(text);
        }
    }

    public boolean verifyPassword(String input, List<Predicate<String>> rules) {
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

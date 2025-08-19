package io.wisoft.javatest.ch5;

import java.util.List;
import java.util.function.Predicate;

public class PasswordVerifierV4 {
    private final List<Predicate<String>> rules;
    private final IComplicatedLogger logger;
    private final MaintenanceWindow maintenanceWindow;

    public PasswordVerifierV4(List<Predicate<String>> rules, IComplicatedLogger logger, MaintenanceWindow maintenanceWindow) {
        this.rules = rules;
        this.logger = logger;
        this.maintenanceWindow = maintenanceWindow;
    }

    public boolean verify(String input) {

        if (this.maintenanceWindow.isUnderMaintenance()) {
            this.logger.info("Under Maintenance", "verify");
            return false;
        }

        long failedCount = this.rules.stream()
                .filter(rule -> !rule.test(input))
                .count();

        if (failedCount == 0) {
            this.logger.info("PASSED", "verify");
            return true;
        }
        this.logger.info("FAIL", "verify");
        return false;
    }
}

package io.wisoft.javatest.ch2;

import java.util.ArrayList;
import java.util.List;

public class PasswordVerifierError {

    private final List<PasswordValidationRule> rules;

    public PasswordVerifierError() {
        rules = new ArrayList<>();
    }

    public void addRule(PasswordValidationRule passwordValidationRule) {
        rules.add(passwordValidationRule);
    }

    public List<String> verifyPassword(String input) {
        List<String> errors = new ArrayList<>();

        if (rules.isEmpty()) {
            throw new IllegalStateException("no rules configured");
        }

        for (PasswordValidationRule rule : rules) {
            ValidationResult result = rule.apply(input);
            if (!result.passed()) {
                errors.add("error " + result.reason());
            }
        }
        return errors;
    }
}

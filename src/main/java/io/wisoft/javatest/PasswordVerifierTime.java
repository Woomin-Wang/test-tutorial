package io.wisoft.javatest;

import java.util.ArrayList;
import java.util.List;

public class PasswordVerifierTime {

    private final List<PasswordValidationRule> rules;

    public PasswordVerifierTime() {
        rules = new ArrayList<>();
    }

    public void addRule(PasswordValidationRule passwordValidationRule) {
        rules.add(passwordValidationRule);
    }

    public List<String> verifyPassword(String input) {
        List<String> errors = new ArrayList<>();
        for (PasswordValidationRule rule : rules) {
            ValidationResult result = rule.apply(input);
            if(!result.passed()) {
                errors.add("error " + result.reason());
            }
        }
        return errors;
    }
}

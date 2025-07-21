package io.wisoft.javatest.ch3;

import io.wisoft.javatest.ch2.PasswordValidationRule;
import io.wisoft.javatest.ch2.ValidationResult;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PasswordVerifierBySupplier {

    private final List<PasswordValidationRule> rules;

    public PasswordVerifierBySupplier() {
        rules = new ArrayList<>();
    }

    public void addRule(PasswordValidationRule passwordValidationRule) {
        rules.add(passwordValidationRule);
    }

    public List<String> verifyPassword(String input, Supplier<DayOfWeek> dayOfWeekSupplier) {

        DayOfWeek dayOfWeek = dayOfWeekSupplier.get();

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalStateException("It's the weekend!");
        }

        List<String> errors = new ArrayList<>();
        for (PasswordValidationRule rule : rules) {
            ValidationResult result = rule.apply(input);
            if (!result.passed()) {
                errors.add("error " + result.reason());
            }
        }
        return errors;
    }
}

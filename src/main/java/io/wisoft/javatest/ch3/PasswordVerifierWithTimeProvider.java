package io.wisoft.javatest.ch3;

import io.wisoft.javatest.ch2.PasswordValidationRule;
import io.wisoft.javatest.ch2.ValidationResult;
import io.wisoft.javatest.ch3.time.RealTimeProvider;
import io.wisoft.javatest.ch3.time.TimeProvider;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class PasswordVerifierWithTimeProvider {

    private final List<PasswordValidationRule> rules;
    private final TimeProvider timeProvider;

    public PasswordVerifierWithTimeProvider(List<PasswordValidationRule> rules, TimeProvider timeProvider) {
        this.rules = rules;
        this.timeProvider = timeProvider;
    }

    public List<String> verifyPassword(String input) {

        DayOfWeek dayOfWeek = timeProvider.getDay();

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

    public static PasswordVerifierWithTimeProvider create(List<PasswordValidationRule> rules) {
        RealTimeProvider realTimeProvider = new RealTimeProvider();
        return new PasswordVerifierWithTimeProvider(rules, realTimeProvider);
    }
}

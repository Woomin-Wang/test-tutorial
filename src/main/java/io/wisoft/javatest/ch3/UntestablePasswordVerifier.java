package io.wisoft.javatest.ch3;

import io.wisoft.javatest.ch2.PasswordValidationRule;
import io.wisoft.javatest.ch2.ValidationResult;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UntestablePasswordVerifier {

    private final List<PasswordValidationRule> rules;

    public UntestablePasswordVerifier() {
        rules = new ArrayList<>();
    }

    public void addRule(PasswordValidationRule passwordValidationRule) {
        rules.add(passwordValidationRule);
    }

    public List<String> verifyPassword(String input) {

        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalStateException("It's the weekend!");
        }

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

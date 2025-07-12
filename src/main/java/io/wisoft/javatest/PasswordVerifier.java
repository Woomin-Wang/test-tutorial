package io.wisoft.javatest;

import java.util.ArrayList;
import java.util.List;

public class PasswordVerifier {

//    const verifyPassword = (input, rules) => {
//    const errors = [];
//        rules.forEach(rule => {
//        const result = rule(input);
//        if (!result.passed) {
//            errors.push(`error ${result.reason}`);
//        }

    private final List<PasswordValidationRule> rules;

    public PasswordVerifier(List<PasswordValidationRule> rules) {
        this.rules = rules;
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

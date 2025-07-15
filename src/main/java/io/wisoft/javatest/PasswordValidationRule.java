package io.wisoft.javatest;

@FunctionalInterface
public interface PasswordValidationRule {

    ValidationResult apply(String input);
}

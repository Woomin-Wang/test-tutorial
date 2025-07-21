package io.wisoft.javatest.ch2;

@FunctionalInterface
public interface PasswordValidationRule {

    ValidationResult apply(String input);
}

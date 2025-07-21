package io.wisoft.javatest.ch3;

import io.wisoft.javatest.ch2.PasswordValidationRule;
import io.wisoft.javatest.ch2.ValidationResult;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class PasswordVerifierFactory {

    public static Function<String, List<String>> makeVerifier(
            List<PasswordValidationRule> rules,
            Supplier<DayOfWeek> dayOfWeekSupplier
    ) {
        return (password) -> {
            DayOfWeek dayOfWeek = dayOfWeekSupplier.get();

            if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                throw new IllegalStateException("It's the weekend!");
            }

            ArrayList<String> errors = new ArrayList<>();

            for (PasswordValidationRule rule : rules) {
                ValidationResult result = rule.apply(password);
                if(!result.passed()) {
                    errors.add("error " + result.reason());
                }
            }
            return errors;
        };
    }
}

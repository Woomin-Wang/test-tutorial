package io.wisoft.javatest.ch2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PasswordVerifier Test")
class PasswordVerifierTestV4 {

    @Nested
    @DisplayName("When a rule fails")
    class FailingRuleScenario {

        @Test
        @DisplayName("has an error message based on the rule's reason")
        void hasErrorMessageBasedOnRuleReason() {
            PasswordVerifier verifier = makeVerifierWithFailingRule();
            List<String> errors = verifier.verifyPassword("any value");

            assertThat(errors).hasSize(1);
            assertThat(errors.getFirst()).contains("fake reason");
        }

        @Test
        @DisplayName("has exactly one error")
        void hasExactlyOneError() {
            PasswordVerifier verifier = makeVerifierWithFailingRule();
            List<String> errors = verifier.verifyPassword("any value");

            assertThat(errors).hasSize(1);
        }
    }

    @Nested
    @DisplayName("When all rules pass")
    class PassingRuleScenario {


        @Test
        @DisplayName("should return no errors")
        void shouldReturnNoErrors() {
            PasswordVerifier verifier = makeVerifierWithPassingRule();
            List<String> errors = verifier.verifyPassword("validPassword123");

            assertThat(errors).isEmpty();
        }

        @Test
        @DisplayName("should handle multiple passing rules")
        void shouldHandleMultiplePassingRules() {
            PasswordVerifier verifier = makeVerifierWithPassingRule();
            List<String> errors = verifier.verifyPassword("validPassword123");

            assertThat(errors).isEmpty();
        }
    }

    @Nested
    @DisplayName("When a mix of passing and failing rules exist")
    class MixedRulesScenario {

        @Test
        @DisplayName("should return only errors from failing rules")
        void shouldReturnOnlyErrorsFromFailingRules() {
            PasswordVerifier verifier = makeVerifierWithAnotherFailingRule();
            List<String> errors = verifier.verifyPassword("any value");

            assertThat(errors).hasSize(2);
            assertThat(errors).containsExactlyInAnyOrder("error fake reason", "error another fake reason");
        }
    }

    private PasswordVerifier makeVerifier() {
        return new PasswordVerifier();
    }

    private PasswordVerifier makeVerifierWithFailingRule() {
        var verifier = makeVerifier();
        verifier.addRule(failingRule);

        return verifier;
    }

    private PasswordVerifier makeVerifierWithPassingRule() {
        var verifier = makeVerifier();
        verifier.addRule(passingRule);

        return verifier;
    }

    private PasswordVerifier makeVerifierWithAnotherFailingRule() {
        var verifier = makeVerifier();
        verifier.addRule(passingRule);
        verifier.addRule(failingRule);
        verifier.addRule(anotherFailingRule);
        verifier.addRule(passingRule);

        return verifier;
    }

    PasswordValidationRule failingRule = input -> new ValidationResult(false, "fake reason");
    PasswordValidationRule passingRule = input -> new ValidationResult(true, "");
    PasswordValidationRule anotherFailingRule = input -> new ValidationResult(false, "another fake reason");
    PasswordValidationRule oneUpperCaseRule = input -> new ValidationResult(!input.toLowerCase().equals(input), "at least one upper case needed");

    @Nested
    @DisplayName("When a password contains at least one uppercase letter")
    class oneUpperCaseRuleTest {

        @ParameterizedTest
        @CsvSource({
            "Abc, true",
            "aBc, true",
            "abc, false"
        })
        @DisplayName("it should return the correct validation result")
        void shouldReturnTrueIfPasswordContainsUppercase(String input, boolean expected) {
            ValidationResult result = oneUpperCaseRule.apply(input);
            Assertions.assertThat(result.passed()).isEqualTo(expected);
        }
    }
}



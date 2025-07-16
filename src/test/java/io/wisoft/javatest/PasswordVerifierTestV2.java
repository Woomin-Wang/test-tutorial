package io.wisoft.javatest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PasswordVerifier Test")
class PasswordVerifierTestV2 {

    PasswordVerifier verifier;

    PasswordValidationRule failingRule = input -> new ValidationResult(false, "fake reason");
    PasswordValidationRule passingRule = input -> new ValidationResult(true, "");
    PasswordValidationRule anotherFailingRule = input -> new ValidationResult(false, "another fake reason");

    @BeforeEach
    void setUp() {
        verifier = new PasswordVerifier();
    }

    @Nested
    @DisplayName("When a rule fails")
    class FailingRuleScenario {

        @BeforeEach
        void setUp() {
            verifier.addRule(failingRule);
        }

        @Test
        @DisplayName("has an error message based on the rule's reason")
        void hasErrorMessageBasedOnRuleReason() {
            List<String> errors = verifier.verifyPassword("any value");

            assertThat(errors).hasSize(1);
            assertThat(errors.getFirst()).contains("fake reason");
        }

        @Test
        @DisplayName("has exactly one error")
        void hasExactlyOneError() {
            List<String> errors = verifier.verifyPassword("any value");

            assertThat(errors).hasSize(1);
        }
    }

    @Nested
    @DisplayName("When all rules pass")
    class PassingRuleScenario {

        @BeforeEach
        void setUp() {
            verifier.addRule(passingRule);
            verifier.addRule(passingRule);
            verifier.addRule(passingRule);
        }

        @Test
        @DisplayName("should return no errors")
        void shouldReturnNoErrors() {
            List<String> errors = verifier.verifyPassword("validPassword123");

            assertThat(errors).isEmpty();
        }

        @Test
        @DisplayName("should handle multiple passing rules")
        void shouldHandleMultiplePassingRules() {
            List<String> errors = verifier.verifyPassword("validPassword123");

            assertThat(errors).isEmpty();
        }
    }

    @Nested
    @DisplayName("When a mix of passing and failing rules exist")
    class MixedRulesScenario {

        @BeforeEach
        void setUp() {
            verifier.addRule(passingRule);
            verifier.addRule(failingRule);
            verifier.addRule(anotherFailingRule);
            verifier.addRule(passingRule);
        }

        @Test
        @DisplayName("should return only errors from failing rules")
        void shouldReturnOnlyErrorsFromFailingRules() {
            List<String> errors = verifier.verifyPassword("any value");

            assertThat(errors).hasSize(2);
            assertThat(errors).containsExactlyInAnyOrder("error fake reason", "error another fake reason");
        }
    }
}



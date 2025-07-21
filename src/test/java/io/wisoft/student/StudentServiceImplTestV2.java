package io.wisoft.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentServiceImplTestV2 {

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        Map<String, Student> studentMap = new HashMap<>();
        studentService = new StudentServiceImpl(studentMap);
    }

    @Nested
    @DisplayName("The create method")
    class CreateTest {
        @Test
        @DisplayName("Should create and return a new student when given a valid Id and name")
        void createWithValidInputsShouldReturnNewStudent() {
            // Given
            String id = "validId";
            String name = "validName";

            // When
            Student result = studentService.create(id, name);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.id()).isEqualTo(id);
            assertThat(result.name()).isEqualTo(name);
        }

        @Test
        @DisplayName("Should throw IllegalStateException when creating a student with a duplicate ID")
        void createWithExistingIdShouldThrowIllegalStateException() {
            // Given
            String duplicateId = "existingId";
            studentService.create(duplicateId, "anyName");

            // When & Then
            IllegalStateException exception = assertThrows(IllegalStateException.class,
                    () -> studentService.create(duplicateId, "anotherName"));

            assertThat(exception.getMessage()).isEqualTo("ID already exists: " + duplicateId);
        }

        @ParameterizedTest
        @CsvSource(value = {
                "NULL, validName",
                "'', validName",
                "'  ', validName",

                "validId, NULL",
                "validId, ''",
                "validId, '  '",

                "NULL, NULL"
        }, nullValues = "NULL")
        @DisplayName("Should throw IllegalArgumentException for null or blank inputs")
        void createWithInvalidInputsShouldThrowIllegalArgumentException(String id, String name) {
            // Given

            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> studentService.create(id, name));

            assertThat(exception.getMessage()).isEqualTo("Student ID and name cannot be blank.");
        }
    }

    @Nested
    @DisplayName("The remove method")
    class RemoveTest {
        @Test
        @DisplayName("Should succeed without exception when removing with an existing Id")
        void removeWithExistingIdShouldSucceedWithoutException() {
            // Given
            String existingId = "existingId";
            studentService.create(existingId, "anyName");

            // When & Then
            assertDoesNotThrow(() -> studentService.remove(existingId));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when removing with a null Id")
        void removeWithNullIdShouldThrowIllegalArgumentException() {
            // Given
            String id = null;

            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> studentService.remove(id));

            assertThat(exception.getMessage()).isEqualTo("Student ID cannot be blank.");
        }

        @Test
        @DisplayName("Should throw IllㅋegalStateException when removing with a non-existing Id")
        void removeWithNonExistingIdShouldThrowIllegalStateException() {
            // Given
            String noExistingId = "noExistingId";

            // When & Then
            IllegalStateException exception = assertThrows(IllegalStateException.class,
                    () -> studentService.remove(noExistingId));

            assertThat(exception.getMessage()).isEqualTo("ID does not exist: " + noExistingId);
        }
    }
}
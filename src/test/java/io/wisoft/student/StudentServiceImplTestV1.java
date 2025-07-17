package io.wisoft.student;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTestV1 {

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

        @Test
        @DisplayName("Should throw IllegalArgumentException when Id and name are null")
        void createWithNullIdAndNameShouldThrowIllegalArgumentException() {
            // Given
            String id = null;
            String name = null;

            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> studentService.create(id, name));

            assertThat(exception.getMessage()).isEqualTo("Student ID and name cannot be blank.");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when Id is null")
        void createWithNullIdShouldThrowIllegalArgumentException() {
            // Given
            String id = null;
            String name = "validName";

            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> studentService.create(id, name));

            assertThat(exception.getMessage()).isEqualTo("Student ID and name cannot be blank.");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when name is null")
        void createWithNullNameShouldThrowIllegalArgumentException() {
            // Given
            String id = "validId";
            String name = null;

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
        @DisplayName("Should throw IllegalStateException when removing with a non-existing Id")
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
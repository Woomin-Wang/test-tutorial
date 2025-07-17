package io.wisoft.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTestV1 {

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        HashMap<String, Student> studentMap = new HashMap<>();
        studentService = new StudentServiceImpl(studentMap);
    }

    @Test
    @DisplayName("Should and return a new student when given a valid Id and name")
    void createWithValidInputsShouldReturnNewStudent() {
        // Given
        String id = "validId";
        String name = "validName";

        // When
        Student result = studentService.create(id, name);

        // Then
        assertThat(result).isNotNull(); // 반환값 확인
        assertThat(result.id()).isEqualTo("validId");
        assertThat(result.name()).isEqualTo("validName");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when creating a student with a duplicate ID")
    void createWithExistingIdShouldThrowIllegalArgumentException() {
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
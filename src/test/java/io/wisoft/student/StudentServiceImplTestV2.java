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
        @DisplayName("유효한 ID와 이름으로 학생 생성 시, 새로운 학생을 반환해야 한다")
        void create_withValidIdAndName_returnsNewStudent() {
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
        @DisplayName("중복된 ID로 학생 생성 시, IllegalStateException을 던져야 한다")
        void create_withExistingId_throwsIllegalStateException() {
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
        @DisplayName("null 또는 빈 입력에 대해 IllegalArgumentException을 던져야 한다")
        void create_withNullOrBlankInputs_throwsIllegalArgumentException(String id, String name) {
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
        @DisplayName("기존 ID로 삭제 시 예외 없이 성공해야 한다")
        void remove_withExistingId_succeedsWithoutException() {
            // Given
            String existingId = "existingId";
            studentService.create(existingId, "anyName");

            // When & Then
            assertDoesNotThrow(() -> studentService.remove(existingId));
        }

        @Test
        @DisplayName("ID가 null일 때 IllegalArgumentException을 던져야 한다")
        void remove_withNullId_throwsIllegalArgumentException() {
            // Given
            String id = null;

            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> studentService.remove(id));

            assertThat(exception.getMessage()).isEqualTo("Student ID cannot be blank.");
        }

        @Test
        @DisplayName("존재하지 않는 ID로 삭제 시 IllegalStateException을 던져야 한다")
        void remove_withNonExistingId_throwsIllegalStateException() {
            // Given
            String noExistingId = "noExistingId";

            // When & Then
            IllegalStateException exception = assertThrows(IllegalStateException.class,
                    () -> studentService.remove(noExistingId));

            assertThat(exception.getMessage()).isEqualTo("ID does not exist: " + noExistingId);
        }
    }
}
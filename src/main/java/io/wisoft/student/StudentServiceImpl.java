package io.wisoft.student;

import io.micrometer.common.util.StringUtils;

import java.util.Map;

public class StudentServiceImpl implements StudentService {

    private final Map<String, Student> studentMap;

    public StudentServiceImpl(Map<String, Student> studentMap) {
        this.studentMap = studentMap;
    }

    @Override
    public Student create(String id, String name) {
        validateInput(id, name);

        Student newStudent = new Student(id, name);
        Student existingStudent = studentMap.putIfAbsent(id, newStudent);

        if(existingStudent != null) {
            throw new IllegalStateException("ID already exists:" + id);
        }
        return newStudent;
    }

    @Override
    public void remove(String id) {
        validateInput(id);

        Student removedStudent = studentMap.remove(id);

        if(removedStudent == null) {
            throw new IllegalStateException("ID does not exist: " + id);
        }
    }

    private void validateInput(String id, String name) {
        if(StringUtils.isBlank(id) || StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Student ID and name cannot be blank.");
        }
    }

    private void validateInput(String id) {
        if(StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("Student ID cannot be blank.");
        }
    }
}

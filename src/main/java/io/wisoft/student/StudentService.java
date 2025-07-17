package io.wisoft.student;

public interface StudentService {

    Student create(String id, String name);

    void remove(String id);
}

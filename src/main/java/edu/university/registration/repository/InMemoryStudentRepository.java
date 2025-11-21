package edu.university.registration.repository;

import edu.university.registration.model.person.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryStudentRepository implements Repository<Student, String> {

    private Map<String, Student> data = new HashMap<>();

    @Override
    public Student save(Student student) {
        if (student == null) return null;
        data.put(student.getId(), student); // Student id = Person id
        return student;
    }

    @Override
    public Student findById(String id) {
        if (id == null) return null;
        return data.get(id);
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public boolean deleteById(String id) {
        if (id == null) return false;
        return data.remove(id) != null;
    }
}

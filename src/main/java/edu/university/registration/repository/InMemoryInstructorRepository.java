package edu.university.registration.repository;

import edu.university.registration.model.person.Instructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryInstructorRepository implements Repository<Instructor, String> {

    private Map<String, Instructor> data = new HashMap<>();

    @Override
    public Instructor save(Instructor instructor) {
        if (instructor == null) return null;
        data.put(instructor.getId(), instructor);
        return instructor;
    }

    @Override
    public Instructor findById(String id) {
        if (id == null) return null;
        return data.get(id);
    }

    @Override
    public List<Instructor> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public boolean deleteById(String id) {
        if (id == null) return false;
        return data.remove(id) != null;
    }
}

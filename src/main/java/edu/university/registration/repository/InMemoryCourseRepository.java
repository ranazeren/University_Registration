package edu.university.registration.repository;

import edu.university.registration.model.course.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCourseRepository implements Repository<Course, String> {

    private Map<String, Course> data = new HashMap<>();

    @Override
    public Course save(Course course) {
        if (course == null) return null;
        data.put(course.getCode(), course);
        return course;
    }

    @Override
    public Course findById(String id) {
        return data.get(id);
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public boolean deleteById(String id) {
        return data.remove(id) != null;
    }
}

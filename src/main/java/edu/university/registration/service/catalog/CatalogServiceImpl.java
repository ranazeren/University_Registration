package edu.university.registration.service.catalog;

import edu.university.registration.model.course.Course;
import edu.university.registration.model.course.Section;
import edu.university.registration.model.course.TimeSlot;
import edu.university.registration.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class CatalogServiceImpl implements CatalogService {

    private final Repository<Course, String> courseRepository;
    private final Repository<Section, String> sectionRepository;

    public CatalogServiceImpl(Repository<Course, String> courseRepository,
                              Repository<Section, String> sectionRepository) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public Course createCourse(String code, String title, int credits) {
        if (code == null || title == null) return null;
        Course course = new Course(code, title, credits);
        return courseRepository.save(course);
    }

    @Override
    public Course findCourse(String code) {
        if (code == null) return null;
        return courseRepository.findById(code);
    }

    @Override
    public List<Course> listAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> search(String keyword) {
        List<Course> result = new ArrayList<>();
        if (keyword == null || keyword.isBlank()) return result;

        String keyLower = keyword.toLowerCase();

        for (Course c : courseRepository.findAll()) {
            if (c.getCode().toLowerCase().contains(keyLower) ||
                    c.getTitle().toLowerCase().contains(keyLower)) {
                result.add(c);
            }
        }

        return result;
    }

    @Override
    public List<Section> listSectionsByInstructor(String instructorId) {
        List<Section> result = new ArrayList<>();
        if (instructorId == null) return result;

        List<Section> all = sectionRepository.findAll();
        for (Section s : all) {
            if (s.getInstructor() != null &&
                    instructorId.equals(s.getInstructor().getId())) {
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public Section createSection(String id,
                                 String courseCode,
                                 String term,
                                 int capacity,
                                 List<TimeSlot> meetingTimes) {
        if (id == null || courseCode == null || term == null) return null;

        Course course = courseRepository.findById(courseCode);
        if (course == null) {
            return null;
        }

        Section section = new Section(id, course, term, null, capacity, meetingTimes);
        return sectionRepository.save(section);
    }

    @Override
    public List<Section> listSectionsByCourse(String courseCode) {
        List<Section> result = new ArrayList<>();
        if (courseCode == null) return result;

        List<Section> all = sectionRepository.findAll();
        for (Section s : all) {
            if (s.getCourse().getCode().equals(courseCode)) {
                result.add(s);
            }
        }
        return result;
    }
}

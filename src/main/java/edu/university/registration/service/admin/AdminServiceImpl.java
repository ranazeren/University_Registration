package edu.university.registration.service.admin;

import edu.university.registration.model.course.Course;
import edu.university.registration.model.person.Instructor;
import edu.university.registration.model.course.Section;
import edu.university.registration.model.course.TimeSlot;
import edu.university.registration.repository.Repository;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    private final Repository<Course, String> courseRepository;
    private final Repository<Section, String> sectionRepository;
    private final Repository<Instructor, String> instructorRepository;

    public AdminServiceImpl(Repository<Course, String> courseRepository,
                            Repository<Section, String> sectionRepository,
                            Repository<Instructor, String> instructorRepository) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Course editCourseTitle(String courseCode, String newTitle) {
        Course c = courseRepository.findById(courseCode);
        if (c == null) return null;

        c.setTitle(newTitle);
        return courseRepository.save(c);
    }

    @Override
    public Course editCourseCredits(String courseCode, int newCredits) {
        Course c = courseRepository.findById(courseCode);
        if (c == null) return null;

        c.setCredits(newCredits);
        return courseRepository.save(c);
    }

    @Override
    public boolean overridePrerequisites(String courseCode, List<String> newPrereqs) {
        Course c = courseRepository.findById(courseCode);
        if (c == null) return false;


        List<String> copy = c.getPrerequisites();
        for (String p : copy) {
            c.removePrerequisite(p);
        }


        for (String p : newPrereqs) {
            c.addPrerequisite(p);
        }

        courseRepository.save(c);
        return true;
    }

    @Override
    public Section editSectionCapacity(String sectionId, int newCapacity) {
        Section s = sectionRepository.findById(sectionId);
        if (s == null) return null;

        s.setCapacity(newCapacity);
        return sectionRepository.save(s);
    }

    @Override
    public Section assignInstructor(String sectionId, String instructorId) {
        Section s = sectionRepository.findById(sectionId);
        Instructor i = instructorRepository.findById(instructorId);

        if (s == null || i == null) return null;

        s.setInstructor(i);
        return sectionRepository.save(s);
    }

    @Override
    public Section changeSectionMeetingTimes(String sectionId, List<TimeSlot> newTimes) {
        Section s = sectionRepository.findById(sectionId);
        if (s == null) return null;


        s.getMeetingTimes().clear();
        s.getMeetingTimes().addAll(newTimes);

        return sectionRepository.save(s);
    }
}

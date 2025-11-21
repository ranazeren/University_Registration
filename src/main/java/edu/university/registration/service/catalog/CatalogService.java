package edu.university.registration.service.catalog;

import edu.university.registration.model.course.Course;
import edu.university.registration.model.course.Section;
import edu.university.registration.model.course.TimeSlot;

import java.util.List;

public interface CatalogService {


    Course createCourse(String code, String title, int credits);

    Course findCourse(String code);


    List<Course> listAllCourses();


    List<Course> search(String keyword);


    List<Section> listSectionsByInstructor(String instructorId);


    Section createSection(String id,
                          String courseCode,
                          String term,
                          int capacity,
                          List<TimeSlot> meetingTimes);


    List<Section> listSectionsByCourse(String courseCode);
}

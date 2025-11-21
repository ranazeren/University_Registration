package edu.university.registration.service.admin;

import edu.university.registration.model.course.Course;
import edu.university.registration.model.course.Section;
import edu.university.registration.model.course.TimeSlot;

import java.util.List;

public interface AdminService {


    Course editCourseTitle(String courseCode, String newTitle);

    Course editCourseCredits(String courseCode, int newCredits);

    boolean overridePrerequisites(String courseCode, List<String> newPrereqs);



    Section editSectionCapacity(String sectionId, int newCapacity);

    Section assignInstructor(String sectionId, String instructorId);


    Section changeSectionMeetingTimes(String sectionId,
                                      List<TimeSlot> newTimes);

}

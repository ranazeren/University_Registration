package edu.university.registration.service.registration;

import edu.university.registration.model.course.Section;

import java.util.List;

public interface RegistrationService {


    boolean enroll(String studentId, String sectionId);


    boolean drop(String studentId, String sectionId);


    List<Section> listSchedule(String studentId, String term);
}

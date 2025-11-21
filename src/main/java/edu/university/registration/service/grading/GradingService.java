package edu.university.registration.service.grading;

import edu.university.registration.model.enrollment.Grade;

public interface GradingService {


    void assignGrade(String instructorId, String sectionId,String studentId, Grade grade);


    double computeGpa(String studentId);
}

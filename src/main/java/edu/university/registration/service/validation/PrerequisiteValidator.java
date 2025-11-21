package edu.university.registration.service.validation;

import edu.university.registration.model.course.Course;
import edu.university.registration.model.enrollment.Grade;
import edu.university.registration.model.person.Student;
import edu.university.registration.model.transcript.Transcript;
import edu.university.registration.model.transcript.TranscriptEntry;

import java.util.List;

public class PrerequisiteValidator {

    public boolean hasCompletedPrerequisites(Student student, Course targetCourse) {
        if (student == null || targetCourse == null) return false;

        List<String> prereqs = targetCourse.getPrerequisites();
        if (prereqs.isEmpty()) {
            return true;
        }

        Transcript transcript = student.getTranscript();
        List<TranscriptEntry> entries = transcript.getEntries();


        for (String prereqCode : prereqs) {
            boolean passed = false;

            for (TranscriptEntry entry : entries) {
                if (prereqCode.equals(entry.getCourseCode())) {
                    Grade g = entry.getGrade();
                    if (g != null && g.points() >= 2.0) {
                        passed = true;
                        break;
                    }
                }
            }

            if (!passed) {
                return false;
            }
        }

        return true;
    }
}
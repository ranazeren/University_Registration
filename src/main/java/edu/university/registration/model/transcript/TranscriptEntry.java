package edu.university.registration.model.transcript;

import edu.university.registration.model.enrollment.Grade;

public class TranscriptEntry {
    private final String courseCode;
    private final int credits;
    private final Grade grade;

    public TranscriptEntry(String courseCode, int credits, Grade grade) {
        this.courseCode = courseCode;
        this.credits = credits;
        this.grade = grade;
    }

    public String getCourseCode() {
        return courseCode;
    }
    public int getCredits() {
        return credits;
    }
    public Grade getGrade() {
        return grade;
    }
}

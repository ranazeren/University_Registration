package edu.university.registration.model.transcript;

import edu.university.registration.model.enrollment.Grade;
import java.util.Objects;

public class TranscriptEntry {

    private final String courseCode;
    private final int credits;
    private final Grade grade;

    public TranscriptEntry(String courseCode, int credits, Grade grade) {

        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("Course code cannot be null or blank");
        }

        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be positive");
        }

        if (grade == null) {
            throw new IllegalArgumentException("Grade cannot be null");
        }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TranscriptEntry)) return false;
        TranscriptEntry that = (TranscriptEntry) o;
        return credits == that.credits &&
                courseCode.equals(that.courseCode) &&
                grade == that.grade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode, credits, grade);
    }

    @Override
    public String toString() {
        return "TranscriptEntry{" +
                "courseCode='" + courseCode + '\'' +
                ", credits=" + credits +
                ", grade=" + grade +
                '}';
    }
}

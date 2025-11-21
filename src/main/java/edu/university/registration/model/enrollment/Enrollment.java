package edu.university.registration.model.enrollment;

import edu.university.registration.model.course.Section;
import edu.university.registration.model.person.Student;

public class Enrollment {

    public enum Status {
        ENROLLED,
        DROPPED,
        WAITLISTED
    }

    private final Student student;
    private final Section section;
    private Status status;
    private Grade grade;

    public Enrollment(Student student, Section section, Status status) {
        if (student == null) throw new IllegalArgumentException("student can not be null");
        if (section == null) throw new IllegalArgumentException("section can not be null");
        if (status == null) throw new IllegalArgumentException("status can not be null");
        this.student = student;
        this.section = section;
        this.status = status;
        this.grade = null;
    }

    public Student getStudent() {
        return student;
    }

    public Section getSection() {
        return section;
    }

    public Status getStatus() {
        return status;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setStatus(Status status) {
        if (status == null) throw new IllegalArgumentException("status can not be null");
        this.status = status;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}

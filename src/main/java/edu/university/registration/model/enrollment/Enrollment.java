package edu.university.registration.model.enrollment;

import edu.university.registration.model.course.Section;
import edu.university.registration.model.person.Student;

import java.util.Objects;

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
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (section == null) {
            throw new IllegalArgumentException("Section cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.student = student;
        this.section = section;
        this.status = status;
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
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }


    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment)) return false;
        Enrollment that = (Enrollment) o;
        return student.equals(that.student) &&
                section.equals(that.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, section);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "student=" + student.getId() +
                ", section=" + section.getId() +
                ", status=" + status +
                ", grade=" + grade +
                '}';
    }
}

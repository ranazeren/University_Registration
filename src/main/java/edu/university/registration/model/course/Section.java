package edu.university.registration.model.course;

import edu.university.registration.model.enrollment.Enrollment;
import edu.university.registration.model.person.Instructor;
import edu.university.registration.model.person.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Section {
    private final String id;
    private final Course course;
    private final String term;
    private Instructor instructor;
    private int capacity;
    private final List<TimeSlot> meetingTimes;
    private final List<Enrollment> roster = new ArrayList<>();

    public Section(String id,
                   Course course,
                   String term,
                   Instructor instructor,
                   int capacity,
                   List<TimeSlot> meetingTimes) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Section id cannot be null or blank");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (term == null || term.isBlank()) {
            throw new IllegalArgumentException("Term cannot be null or blank");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        if (meetingTimes == null || meetingTimes.isEmpty()) {
            throw new IllegalArgumentException("Meeting times cannot be null or empty");
        }
        for (TimeSlot ts : meetingTimes) {
            if (ts == null) {
                throw new IllegalArgumentException("Meeting time cannot contain null elements");
            }
        }

        this.id = id;
        this.course = course;
        this.term = term;
        this.instructor = instructor;
        this.capacity = capacity;
        this.meetingTimes = new ArrayList<>(meetingTimes);
    }

    public String getId() { return id; }
    public Course getCourse() { return course; }
    public String getTerm() { return term; }
    public Instructor getInstructor() { return instructor; }
    public int getCapacity() { return capacity; }

    public List<TimeSlot> getMeetingTimes() {
        return List.copyOf(meetingTimes);
    }

    public List<Enrollment> getRoster() {
        return List.copyOf(roster);
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        this.capacity = capacity;
    }

    public boolean isFull() {
        return roster.size() >= capacity;
    }

    public boolean addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (isFull()) return false;

        for (Enrollment e : roster) {
            if (e.getStudent().equals(student)) {
                return false;
            }
        }

        Enrollment enrollment = new Enrollment(student, this, Enrollment.Status.ENROLLED);
        roster.add(enrollment);
        return true;
    }

    public boolean removeStudent(Student student) {
        if (student == null) {
            return false;
        }
        return roster.removeIf(e -> e.getStudent().equals(student));
    }

    public boolean conflictsWith(Section other) {
        Objects.requireNonNull(other, "Other section cannot be null");
        for (TimeSlot t1 : this.meetingTimes) {
            for (TimeSlot t2 : other.meetingTimes) {
                if (t1.overlaps(t2)) {
                    return true;
                }
            }
        }
        return false;
    }
}

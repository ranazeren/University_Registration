package edu.university.registration.model.course;

import edu.university.registration.model.enrollment.Enrollment;
import edu.university.registration.model.person.Person;
import edu.university.registration.model.person.Student;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Section {
    private final String id;
    private final Course course;
    private final String term;
    private Person instructor;
    private int capacity;
    private  List<TimeSlot> meetingTimes = new ArrayList<>();
    private  List<Enrollment> roster = new ArrayList<>();

    public Section(String id, Course course, String term, Person instructor, int capacity, List<TimeSlot> meetingTimes) {

        if (id == null) throw new IllegalArgumentException();
        if (course == null) throw new IllegalArgumentException();
        if (term == null) throw new IllegalArgumentException();
        if (capacity < 0) throw new IllegalArgumentException();
        if (meetingTimes == null || meetingTimes.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.course = course;
        this.term = term;
        this.instructor = instructor;
        this.capacity = capacity;

        for (TimeSlot ts : meetingTimes) {
            if (ts == null) throw new IllegalArgumentException();
        }
        this.meetingTimes = new ArrayList<>(meetingTimes);
    }

    public String getId() { return id; }
    public Course getCourse() { return course; }
    public String getTerm() { return term; }
    public Person getInstructor() { return instructor; }
    public int getCapacity() { return capacity; }
    public List<TimeSlot> getMeetingTimes() { return meetingTimes; }
    public List<Enrollment> getRoster() { return roster; }

    public void setInstructor(Person instructor) {
        this.instructor = instructor;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException();
        this.capacity = capacity;
    }

    public boolean isFull() {
        return roster.size() >= capacity;
    }


    public boolean addStudent(Student student) {
        if (student == null) throw new IllegalArgumentException();
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
        if (student == null) return false;

        return roster.removeIf(e -> e.getStudent().equals(student));
    }

    public boolean conflictsWith(Section other) {

        for (TimeSlot t1 : this.meetingTimes) {
            for (TimeSlot t2 : other.meetingTimes) {
                if (t1.getDayOfWeek() == t2.getDayOfWeek()) {
                    LocalTime aStart = t1.getStart();
                    LocalTime aEnd   = t1.getEnd();
                    LocalTime bStart = t2.getStart();
                    LocalTime bEnd   = t2.getEnd();


                    if (aStart.isBefore(bEnd) && bStart.isBefore(aEnd)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

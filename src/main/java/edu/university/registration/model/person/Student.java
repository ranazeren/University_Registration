package edu.university.registration.model.person;

import edu.university.registration.model.enrollment.Grade;
import edu.university.registration.model.transcript.Transcript;
import edu.university.registration.model.transcript.TranscriptEntry;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {

    private String major;
    private final List<String> currentRegistrations = new ArrayList<>();
    private final Transcript transcript = new Transcript();

    public Student(String id, String name, String email, String major) {
        super(id, name, email);
        if (major == null || major.isBlank()) {
            throw new IllegalArgumentException("Major cannot be null or blank");
        }
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        if (major == null || major.isBlank()) {
            throw new IllegalArgumentException("Major cannot be null or blank");
        }
        this.major = major;
    }

    public List<String> getCurrentRegistrations() {
        return List.copyOf(currentRegistrations);
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public double getGpa() {
        return transcript.computeGpa();
    }


    public void addTranscriptEntry(String courseCode, int credits, Grade grade) {
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("Course code cannot be null or blank");
        }
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be positive");
        }
        if (grade == null) {
            throw new IllegalArgumentException("Grade cannot be null");
        }
        TranscriptEntry entry = new TranscriptEntry(courseCode, credits, grade);
        transcript.addEntry(entry);
    }

    public boolean addCurrentRegistration(String sectionId) {
        if (sectionId == null || sectionId.isBlank()) {
            throw new IllegalArgumentException("Section ID cannot be null or blank");
        }
        if (currentRegistrations.contains(sectionId)) {
            return false;
        }
        currentRegistrations.add(sectionId);
        return true;
    }

    public boolean removeCurrentRegistration(String sectionId) {
        if (sectionId == null || sectionId.isBlank()) {
            return false;
        }
        return currentRegistrations.remove(sectionId);
    }

    @Override
    public String role() {
        return "STUDENT";
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", major='" + major + '\'' +
                ", currentRegistrations=" + currentRegistrations +
                '}';
    }
}

package edu.university.registration.model.person;



import edu.university.registration.model.enrollment.Grade;
import edu.university.registration.model.transcript.Transcript;
import edu.university.registration.model.transcript.TranscriptEntry;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {


    private String major;
    private List<String> currentRegistrations = new ArrayList<>();
    private Transcript transcript = new Transcript();


    public Student(String id, String name, String email, String major) {
        super(id, name, email);
        this.major = major;
    }


    public String getMajor() {
        return major;
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



    public void setMajor(String major) {
        this.major = major;
    }
    public void addTranscriptEntry(String courseCode, int credits, Grade grade) {
        TranscriptEntry entry = new TranscriptEntry(courseCode, credits, grade);
        transcript.addEntry(entry);
    }
    public void addCurrentRegistration(String sectionId) {
        if (sectionId == null) return;
        if (!currentRegistrations.contains(sectionId)) {
            currentRegistrations.add(sectionId);
        }
    }

    public void removeCurrentRegistration(String sectionId) {
        currentRegistrations.remove(sectionId);
    }




    @Override
    public String role() {
        return "STUDENT";
    }
}

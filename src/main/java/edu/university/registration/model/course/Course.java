package edu.university.registration.model.course;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String code;
    private String title;
    private int credits;
    private List<String> prerequisites = new ArrayList<>();

    public Course(String code, String title, int credits) {
        this.code = code;
        this.title = title;
        this.credits = credits;
    }

    public Course(String code, String title, int credits, List<String> prerequisites) {
        this(code, title, credits);
        if (prerequisites != null) {
            this.prerequisites.addAll(prerequisites);
        }
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public List<String> getPrerequisites() {
        return List.copyOf(prerequisites);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void addPrerequisite(String courseCode) {
        if (!prerequisites.contains(courseCode)) {
            prerequisites.add(courseCode);
        }
    }

    public void removePrerequisite(String courseCode) {
        prerequisites.remove(courseCode);
    }
}

package edu.university.registration.model.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    private final String code;
    private String title;
    private int credits;
    private final List<String> prerequisites = new ArrayList<>();

    public Course(String code, String title, int credits) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Course code cannot be null ");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Course title cannot be null ");
        }
        if (credits <= 0) {
            throw new IllegalArgumentException("Course credits must be positive");
        }
        this.code = code;
        this.title = title;
        this.credits = credits;
    }

    public Course(String code, String title, int credits, List<String> prerequisites) {
        this(code, title, credits);
        if (prerequisites != null) {
            for (String p : prerequisites) {
                addPrerequisite(p);
            }
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
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Course title cannot be null ");
        }
        this.title = title;
    }

    public void setCredits(int credits) {
        if (credits <= 0) {
            throw new IllegalArgumentException("Course credits must be positive");
        }
        this.credits = credits;
    }

    public void addPrerequisite(String courseCode) {
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("Prerequisite course code cannot be null .");
        }
        if (!prerequisites.contains(courseCode)) {
            prerequisites.add(courseCode);
        }
    }

    public void removePrerequisite(String courseCode) {
        if (courseCode == null) return;
        prerequisites.remove(courseCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return code.equals(course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}

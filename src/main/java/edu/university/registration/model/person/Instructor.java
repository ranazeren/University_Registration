package edu.university.registration.model.person;

import java.util.ArrayList;
import java.util.List;


public class Instructor extends Person {

    private String department;
    private final List<String> assignedSectionIds = new ArrayList<>();

    public Instructor(String id, String name, String email, String department) {
        super(id, name, email);

        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Department cannot be null or blank");
        }

        this.department = department;
    }

    public boolean assignSection(String sectionId) {
        if (sectionId == null || sectionId.isBlank()) {
            throw new IllegalArgumentException("Section ID cannot be null or blank");
        }
        if (assignedSectionIds.contains(sectionId)) {
            return false;
        }
        assignedSectionIds.add(sectionId);
        return true;
    }

    public boolean removeAssignedSection(String sectionId) {
        if (sectionId == null || sectionId.isBlank()) {
            return false;
        }
        return assignedSectionIds.remove(sectionId);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Department cannot be null or blank");
        }
        this.department = department;
    }

    public List<String> getAssignedSectionIds() {
        return List.copyOf(assignedSectionIds);
    }

    @Override
    public String role() {
        return "INSTRUCTOR";
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", department='" + department + '\'' +
                ", assignedSections=" + assignedSectionIds +
                '}';
    }
}

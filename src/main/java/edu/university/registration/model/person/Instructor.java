package edu.university.registration.model.person;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {
    private String department;
    private List<String> assignedSectionIds = new ArrayList<>();

    public Instructor(String id, String name, String email, String department) {
        super(id, name, email);
        this.department = department;
    }
    public void assignSection(String sectionId) {
        assignedSectionIds.add(sectionId);
    }

    public void removeAssignedSection(String sectionId) {
        assignedSectionIds.remove(sectionId);
    }


    public String getDepartment() {

        return department;
    }

    public List<String> getAssignedSectionIds() {

        return List.copyOf(assignedSectionIds);
    }

    public void setDepartment(String department) {

        this.department = department;
    }

    @Override
    public String role() {
        return "INSTRUCTOR";
    }
}

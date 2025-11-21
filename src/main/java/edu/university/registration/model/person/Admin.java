package edu.university.registration.model.person;

public class Admin extends Person {

    public Admin(String id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public String role() {
        return "ADMIN";
    }
}

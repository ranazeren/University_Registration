package edu.university.registration.model.enrollment;

public enum Grade{
    A(4.0),
    B(3.0),
    C(2.0),
    D(1.0),
    F(0.0),
    I(null),
    W(null);
    private final Double gp ;

    Grade(Double gp) {
        this.gp = gp;
    }
    public boolean countsInGpa(){
        return gp != null;
    }
    public double points(){
        return gp == null ? 0 : gp ;
    }
}
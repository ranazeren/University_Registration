package edu.university.registration.service.validation;

import edu.university.registration.model.course.Section;

public interface CapacityValidator {

    boolean hasCapacity(Section section);
}
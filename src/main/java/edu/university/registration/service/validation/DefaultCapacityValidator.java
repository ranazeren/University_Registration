package edu.university.registration.service.validation;

import edu.university.registration.model.course.Section;

public class DefaultCapacityValidator implements CapacityValidator {

    @Override
    public boolean hasCapacity(Section section) {
        if (section == null) return false;
        return !section.isFull();
    }
}
package edu.university.registration.service.validation;

import edu.university.registration.model.course.Section;

import java.util.List;

public interface ScheduleConflictChecker {

    boolean hasConflict(List<Section> currentSchedule, Section candidate);
}

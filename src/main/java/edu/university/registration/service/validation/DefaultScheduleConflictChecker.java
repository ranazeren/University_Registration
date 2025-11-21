package edu.university.registration.service.validation;

import edu.university.registration.model.course.Section;

import java.util.List;

public class DefaultScheduleConflictChecker implements ScheduleConflictChecker {

    @Override
    public boolean hasConflict(List<Section> currentSchedule, Section candidate) {
        if (currentSchedule == null || candidate == null) return false;

        for (Section s : currentSchedule) {
            if (s.conflictsWith(candidate)) {
                return true;
            }
        }
        return false;
    }
}
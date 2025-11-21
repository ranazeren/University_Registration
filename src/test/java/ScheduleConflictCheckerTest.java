

import edu.university.registration.model.course.Course;
import edu.university.registration.model.course.Section;
import edu.university.registration.model.course.TimeSlot;
import edu.university.registration.service.validation.DefaultScheduleConflictChecker;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultScheduleConflictCheckerTest {

    private Section dummySection(String id, TimeSlot... timeSlots) {
        Course c = new Course("CS101", "Intro", 3, List.of());
        return new Section(
                id,
                c,
                "2025FALL",
                null,
                30,
                List.of(timeSlots)
        );
    }

    @Test
    void conflictExists_whenOverlappingTimeSlots() {
        TimeSlot t1 = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(10,0), LocalTime.of(11,0), "R1");
        TimeSlot t2 = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(10,30), LocalTime.of(11,30), "R2");

        Section s1 = dummySection("S1", t1);
        Section candidate = dummySection("S2", t2);

        DefaultScheduleConflictChecker checker = new DefaultScheduleConflictChecker();

        assertTrue(checker.hasConflict(List.of(s1), candidate));
    }

    @Test
    void noConflict_whenBackToBackSections() {
        TimeSlot t1 = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(8,0), LocalTime.of(9,0), "R1");
        TimeSlot t2 = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(10,0), "R2");

        Section s1 = dummySection("S1", t1);
        Section candidate = dummySection("S2", t2);

        DefaultScheduleConflictChecker checker = new DefaultScheduleConflictChecker();

        assertFalse(checker.hasConflict(List.of(s1), candidate));
    }

    @Test
    void noConflict_whenDifferentDays() {
        TimeSlot t1 = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(10,0), LocalTime.of(11,0), "R1");
        TimeSlot t2 = new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(10,0), LocalTime.of(11,0), "R2");

        Section s1 = dummySection("S1", t1);
        Section candidate = dummySection("S2", t2);

        DefaultScheduleConflictChecker checker = new DefaultScheduleConflictChecker();

        assertFalse(checker.hasConflict(List.of(s1), candidate));
    }
}


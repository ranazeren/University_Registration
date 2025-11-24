


import edu.university.registration.model.course.TimeSlot;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {

    @Test
    void overlaps_sameDay_overlappingIntervals_returnsTrue() {
        TimeSlot a = new TimeSlot(DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                "R1");

        TimeSlot b = new TimeSlot(DayOfWeek.MONDAY,
                LocalTime.of(10, 30),
                LocalTime.of(11, 30),
                "R2");

        assertTrue(a.overlaps(b));
        assertTrue(b.overlaps(a));
    }

    @Test
    void overlaps_sameDay_touchingAtBoundary_returnsFalse() {
        TimeSlot a = new TimeSlot(DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                "R1");

        TimeSlot b = new TimeSlot(DayOfWeek.MONDAY,
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                "R2");

        assertFalse(a.overlaps(b));
        assertFalse(b.overlaps(a));
    }

    @Test
    void overlaps_differentDays_returnsFalse() {
        TimeSlot a = new TimeSlot(DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                "R1");

        TimeSlot b = new TimeSlot(DayOfWeek.TUESDAY,
                LocalTime.of(10, 30),
                LocalTime.of(11, 30),
                "R2");

        assertFalse(a.overlaps(b));
    }

    @Test
    void constructor_startNotBeforeEnd_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new TimeSlot(DayOfWeek.MONDAY,
                        LocalTime.of(11, 0),
                        LocalTime.of(10, 0),
                        "R1"));
    }
}

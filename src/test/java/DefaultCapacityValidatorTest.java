

import edu.university.registration.model.course.Course;
import edu.university.registration.model.course.Section;
import edu.university.registration.model.course.TimeSlot;
import edu.university.registration.model.person.Student;
import edu.university.registration.service.validation.DefaultCapacityValidator;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultCapacityValidatorTest {

    private Section dummySectionWithCapacity(int capacity) {
        Course c = new Course("CS101", "Intro", 3, List.of());
        TimeSlot t = new TimeSlot(
                DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                "R1"
        );
        return new Section(
                "S1",
                c,
                "2025FALL",
                null,
                capacity,
                List.of(t)
        );
    }


    private Student dummyStudent(String id) {
        return new Student(id, "Test Student " + id, "test" + id + "@mail.com", "CS");
    }

    @Test
    void hasCapacity_returnsTrue_whenSectionNotFull() {
        Section section = dummySectionWithCapacity(2);
        DefaultCapacityValidator validator = new DefaultCapacityValidator();


        assertTrue(validator.hasCapacity(section));
    }

    @Test
    void hasCapacity_returnsFalse_whenSectionIsFull() {
        Section section = dummySectionWithCapacity(2);


        section.addStudent(dummyStudent("S1"));
        section.addStudent(dummyStudent("S2"));

        DefaultCapacityValidator validator = new DefaultCapacityValidator();


        assertFalse(validator.hasCapacity(section));
    }

    @Test
    void hasCapacity_returnsFalse_whenSectionIsNull() {
        DefaultCapacityValidator validator = new DefaultCapacityValidator();

        assertFalse(validator.hasCapacity(null));
    }
}

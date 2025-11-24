

import edu.university.registration.model.course.Course;
import edu.university.registration.model.enrollment.Grade;
import edu.university.registration.model.person.Student;
import edu.university.registration.service.validation.PrerequisiteValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrerequisiteValidatorTest {

    private PrerequisiteValidator validator;
    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student("S1", "Alice", "alice@example.com", "CS");
        validator = new PrerequisiteValidator();
    }


    @Test
    void whenStudentIsNull_shouldReturnFalse() {
        Course course = new Course("CS101", "Intro", 4);

        boolean result = validator.hasCompletedPrerequisites(null, course);

        assertFalse(result);
    }


    @Test
    void whenCourseIsNull_shouldReturnFalse() {
        boolean result = validator.hasCompletedPrerequisites(student, null);

        assertFalse(result);
    }


    @Test
    void courseWithNoPrerequisites_shouldReturnTrue() {
        Course noPrereq = new Course("CS101", "Intro", 4);

        boolean result = validator.hasCompletedPrerequisites(student, noPrereq);

        assertTrue(result, "A course with no prerequisites should always pass.");
    }


    @Test
    void singlePrerequisiteSatisfied_shouldReturnTrue() {
        student.addTranscriptEntry("CS100", 4, Grade.B);

        Course target = new Course("CS101", "Intro", 4, List.of("CS100"));

        boolean result = validator.hasCompletedPrerequisites(student, target);

        assertTrue(result, "Student completed the required course with passing grade.");
    }


    @Test
    void singlePrerequisiteNotTaken_shouldReturnFalse() {
        Course target = new Course("CS101", "Intro", 4, List.of("CS100"));

        boolean result = validator.hasCompletedPrerequisites(student, target);

        assertFalse(result, "Student did not take the prerequisite course.");
    }


    @Test
    void singlePrerequisiteWithFailingGrade_shouldReturnFalse() {
        student.addTranscriptEntry("CS100", 4, Grade.D);

        Course target = new Course("CS101", "Intro", 4, List.of("CS100"));

        boolean result = validator.hasCompletedPrerequisites(student, target);

        assertFalse(result, "Student received too low grade to satisfy the prerequisite.");
    }

    @Test
    void multiplePrerequisitesSatisfied_shouldReturnTrue() {
        student.addTranscriptEntry("CS100", 4, Grade.B);
        student.addTranscriptEntry("MATH101", 4, Grade.A);

        Course target = new Course("CS201", "Data Structures", 4, List.of("CS100", "MATH101"));

        boolean result = validator.hasCompletedPrerequisites(student, target);

        assertTrue(result, "Student completed all prerequisites with passing grades.");
    }


    @Test
    void multiplePrerequisitesMissingOne_shouldReturnFalse() {
        student.addTranscriptEntry("CS100", 4, Grade.B);

        Course target = new Course("CS201", "Data Structures", 4, List.of("CS100", "MATH101"));

        boolean result = validator.hasCompletedPrerequisites(student, target);

        assertFalse(result, "At least one required prerequisite is missing.");
    }


    @Test
    void multiplePrerequisitesOneFailing_shouldReturnFalse() {
        student.addTranscriptEntry("CS100", 4, Grade.B);
        student.addTranscriptEntry("MATH101", 4, Grade.D);

        Course target = new Course("CS201", "Data Structures", 4, List.of("CS100", "MATH101"));

        boolean result = validator.hasCompletedPrerequisites(student, target);

        assertFalse(result, "One prerequisite has an insufficient grade.");
    }
}

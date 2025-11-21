

import edu.university.registration.model.course.Course;
import edu.university.registration.model.course.Section;
import edu.university.registration.model.course.TimeSlot;
import edu.university.registration.model.enrollment.Grade;
import edu.university.registration.model.person.Student;
import edu.university.registration.model.transcript.TranscriptEntry;
import edu.university.registration.repository.Repository;
import edu.university.registration.service.registration.RegistrationServiceImpl;
import edu.university.registration.service.validation.CapacityValidator;
import edu.university.registration.service.validation.DefaultCapacityValidator;
import edu.university.registration.service.validation.DefaultScheduleConflictChecker;
import edu.university.registration.service.validation.PrerequisiteValidator;
import edu.university.registration.service.validation.ScheduleConflictChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {


    private static class InMemoryRepository<T> implements Repository<T, String> {
        private final Map<String, T> data = new HashMap<>();

        @Override
        public T findById(String id) {
            return data.get(id); //
        }

        @Override
        public java.util.List<T> findAll() {
            return new ArrayList<>(data.values());
        }

        @Override
        public T save(T entity) {
            return entity;
        }

        @Override
        public boolean deleteById(String id) {
            return data.remove(id) != null;
        }



        void put(String id, T entity) {
            data.put(id, entity);
        }
    }

    private InMemoryRepository<Student> studentRepo;
    private InMemoryRepository<Section> sectionRepo;

    private PrerequisiteValidator prereqValidator;
    private CapacityValidator capacityValidator;
    private ScheduleConflictChecker conflictChecker;

    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        studentRepo = new InMemoryRepository<>();
        sectionRepo = new InMemoryRepository<>();

        prereqValidator = new edu.university.registration.service.validation.PrerequisiteValidator(); // sende class bu isimde
        capacityValidator = new DefaultCapacityValidator();
        conflictChecker = new DefaultScheduleConflictChecker();

        registrationService = new RegistrationServiceImpl(
                studentRepo,
                sectionRepo,
                prereqValidator,
                capacityValidator,
                conflictChecker
        );
    }

    private Course makeCourse(String code, java.util.List<String> prereqs) {

        return new Course(code, "Test " + code, 3, prereqs);
    }

    private Section makeSection(String id, Course course, String term, TimeSlot... timeSlots) {
        return new Section(
                id,
                course,
                term,
                null,
                10,
                java.util.List.of(timeSlots)
        );
    }

    private Student makeStudentWithPassed(String id, String... passedCourses) {
        Student s = new Student(id, "Student " + id, id + "@mail.com", "CS");
        for (String c : passedCourses) {
            s.addTranscriptEntry(c, 3, Grade.B);
        }
        return s;
    }


    @Test
    void enroll_success_whenPrereqOkCapacityOkNoConflict() {
        Course cs101 = makeCourse("CS101", java.util.List.of());
        Course cs201 = makeCourse("CS201", java.util.List.of("CS101"));

        Student student = makeStudentWithPassed("S1", "CS101");
        studentRepo.put("S1", student);

        TimeSlot slot = new TimeSlot(DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                "R1");
        Section target = makeSection("SEC1", cs201, "2025FALL", slot);
        sectionRepo.put("SEC1", target);

        boolean result = registrationService.enroll("S1", "SEC1");

        assertTrue(result);
        assertEquals(1, target.getRoster().size());
        assertEquals("S1", target.getRoster().get(0).getStudent().getId());
    }


    @Test
    void enroll_fails_whenMissingPrerequisite() {
        Course cs101 = makeCourse("CS101", java.util.List.of());
        Course cs201 = makeCourse("CS201", java.util.List.of("CS101"));


        Student student = new Student("S2", "Student 2", "s2@mail.com", "CS");
        studentRepo.put("S2", student);

        TimeSlot slot = new TimeSlot(DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                "R1");
        Section target = makeSection("SEC2", cs201, "2025FALL", slot);
        sectionRepo.put("SEC2", target);

        boolean result = registrationService.enroll("S2", "SEC2");

        assertFalse(result);
        assertEquals(0, target.getRoster().size());
    }


    @Test
    void enroll_fails_whenScheduleConflict() {
        Course cs101 = makeCourse("CS101", java.util.List.of());
        Course cs201 = makeCourse("CS201", java.util.List.of("CS101"));

        Student student = makeStudentWithPassed("S3", "CS101");
        studentRepo.put("S3", student);

        TimeSlot t1 = new TimeSlot(DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                "R1");
        TimeSlot t2 = new TimeSlot(DayOfWeek.MONDAY,
                LocalTime.of(10, 30),
                LocalTime.of(11, 30),
                "R2");

        Section existing = makeSection("SEC_EXIST", cs201, "2025FALL", t1);
        Section candidate = makeSection("SEC_NEW", cs201, "2025FALL", t2);


        existing.addStudent(student);

        sectionRepo.put("SEC_EXIST", existing);
        sectionRepo.put("SEC_NEW", candidate);

        boolean result = registrationService.enroll("S3", "SEC_NEW");

        assertFalse(result);
        assertEquals(1, existing.getRoster().size());
        assertEquals(0, candidate.getRoster().size());
    }
}

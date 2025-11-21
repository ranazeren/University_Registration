

import edu.university.registration.model.course.Course;
import edu.university.registration.repository.InMemoryCourseRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryCourseRepositoryTest {

    @Test
    void saveAndFindById_shouldReturnSameCourse() {
        InMemoryCourseRepository repo = new InMemoryCourseRepository();

        Course course = new Course("CS101", "Intro to CS", 3);
        repo.save(course);

        Course found = repo.findById("CS101");

        assertNotNull(found);
        assertEquals("CS101", found.getCode());
        assertEquals("Intro to CS", found.getTitle());
        assertEquals(3, found.getCredits());
    }

    @Test
    void findAll_shouldReturnAllSavedCourses() {
        InMemoryCourseRepository repo = new InMemoryCourseRepository();

        Course c1 = new Course("CS101", "Intro to CS", 3);
        Course c2 = new Course("MATH101", "Calculus I", 4);

        repo.save(c1);
        repo.save(c2);

        List<Course> all = repo.findAll();

        assertEquals(2, all.size());

        assertTrue(all.stream().anyMatch(c -> "CS101".equals(c.getCode())));
        assertTrue(all.stream().anyMatch(c -> "MATH101".equals(c.getCode())));
    }

    @Test
    void deleteById_shouldRemoveCourse() {
        InMemoryCourseRepository repo = new InMemoryCourseRepository();

        Course course = new Course("CS101", "Intro to CS", 3);
        repo.save(course);

        boolean deleted = repo.deleteById("CS101");

        assertTrue(deleted);
        assertNull(repo.findById("CS101"));
        assertTrue(repo.findAll().isEmpty());
    }
}

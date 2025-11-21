

import edu.university.registration.model.enrollment.Grade;
import edu.university.registration.model.transcript.Transcript;
import edu.university.registration.model.transcript.TranscriptEntry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranscriptTest {

    @Test
    void gpaIsZero_whenNoEntries() {
        Transcript t = new Transcript();
        assertEquals(0.0, t.computeGpa());
    }

    @Test
    void computeGpa_singleCourse() {
        Transcript t = new Transcript();
        t.addEntry(new TranscriptEntry("CS101", 3, Grade.A));


        assertEquals(4.0, t.computeGpa());
    }

    @Test
    void computeGpa_multipleCoursesCorrectlyWeighted() {
        Transcript t = new Transcript();
        t.addEntry(new TranscriptEntry("CS101", 3, Grade.A));
        t.addEntry(new TranscriptEntry("MATH201", 4, Grade.B));
        assertEquals(24.0 / 7.0, t.computeGpa());
    }

    @Test
    void computeGpa_skipGradesThatDontCount() {
        Transcript t = new Transcript();
        t.addEntry(new TranscriptEntry("CS101", 3, Grade.W));
        t.addEntry(new TranscriptEntry("CS102", 3, Grade.A));

        assertEquals(4.0, t.computeGpa());
    }

    @Test
    void computeGpa_ignoreNullGrades() {
        Transcript t = new Transcript();
        t.addEntry(new TranscriptEntry("CS101", 3, null));
        t.addEntry(new TranscriptEntry("CS102", 3, Grade.B));

        assertEquals(3.0, t.computeGpa());
    }
}

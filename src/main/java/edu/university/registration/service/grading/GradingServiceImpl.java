package edu.university.registration.service.grading;

import edu.university.registration.model.enrollment.Enrollment;
import edu.university.registration.model.enrollment.Grade;
import edu.university.registration.model.course.Section;
import edu.university.registration.model.person.Student;
import edu.university.registration.repository.Repository;

import java.util.List;

public class GradingServiceImpl implements GradingService {

    private final Repository<Student, String> studentRepository;
    private final Repository<Section, String> sectionRepository;

    public GradingServiceImpl(Repository<Student, String> studentRepository,
                              Repository<Section, String> sectionRepository) {
        this.studentRepository = studentRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public void assignGrade(String instructorId, String sectionId, String studentId, Grade grade) {

        if (sectionId == null || studentId == null || grade == null) return;

        Section section = sectionRepository.findById(sectionId);
        Student student = studentRepository.findById(studentId);

        if (section == null || student == null) return;


        List<Enrollment> roster = section.getRoster();
        for (Enrollment e : roster) {
            if (e.getStudent().getId().equals(studentId)) {
                e.setGrade(grade);
                break;
            }
        }


        sectionRepository.save(section);
    }

    @Override
    public double computeGpa(String studentId) {
        if (studentId == null) return 0.0;
        Student student = studentRepository.findById(studentId);
        if (student == null) return 0.0;

        return student.getGpa();
    }
}

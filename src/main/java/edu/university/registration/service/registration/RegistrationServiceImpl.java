package edu.university.registration.service.registration;

import edu.university.registration.model.enrollment.Enrollment;
import edu.university.registration.model.course.Section;
import edu.university.registration.model.person.Student;
import edu.university.registration.repository.Repository;
import edu.university.registration.service.validation.CapacityValidator;
import edu.university.registration.service.validation.PrerequisiteValidator;
import edu.university.registration.service.validation.ScheduleConflictChecker;

import java.util.ArrayList;
import java.util.List;

public class RegistrationServiceImpl implements RegistrationService {

    private final Repository<Student, String> studentRepository;
    private final Repository<Section, String> sectionRepository;
    private final PrerequisiteValidator prerequisiteValidator;
    private final CapacityValidator capacityValidator;
    private final ScheduleConflictChecker scheduleConflictChecker;

    public RegistrationServiceImpl(Repository<Student, String> studentRepository,
                                   Repository<Section, String> sectionRepository,
                                   PrerequisiteValidator prerequisiteValidator,
                                   CapacityValidator capacityValidator,
                                   ScheduleConflictChecker scheduleConflictChecker) {
        this.studentRepository = studentRepository;
        this.sectionRepository = sectionRepository;
        this.prerequisiteValidator = prerequisiteValidator;
        this.capacityValidator = capacityValidator;
        this.scheduleConflictChecker = scheduleConflictChecker;
    }

    @Override
    public boolean enroll(String studentId, String sectionId) {
        if (studentId == null || sectionId == null) return false;

        Student student = studentRepository.findById(studentId);
        Section section = sectionRepository.findById(sectionId);

        if (student == null || section == null) {
            return false;
        }


        if (!prerequisiteValidator.hasCompletedPrerequisites(student, section.getCourse())) {
            return false;
        }


        List<Section> currentSchedule = listSchedule(studentId, section.getTerm());


        if (scheduleConflictChecker.hasConflict(currentSchedule, section)) {
            return false;
        }


        if (!capacityValidator.hasCapacity(section)) {
            return false;
        }


        boolean added = section.addStudent(student);
        if (!added) {
            return false;
        }


        sectionRepository.save(section);
        return true;
    }

    @Override
    public boolean drop(String studentId, String sectionId) {
        if (studentId == null || sectionId == null) return false;

        Student student = studentRepository.findById(studentId);
        Section section = sectionRepository.findById(sectionId);

        if (student == null || section == null) {
            return false;
        }

        List<Enrollment> roster = section.getRoster();
        boolean removed = false;

        for (int i = 0; i < roster.size(); i++) {
            Enrollment e = roster.get(i);
            if (e.getStudent().getId().equals(studentId)) {
                roster.remove(i);
                removed = true;
                break;
            }
        }

        if (removed) {
            sectionRepository.save(section);
        }

        return removed;
    }

    @Override
    public List<Section> listSchedule(String studentId, String term) {
        List<Section> result = new ArrayList<>();
        if (studentId == null || term == null) return result;

        List<Section> allSections = sectionRepository.findAll();

        for (Section section : allSections) {
            if (!term.equals(section.getTerm())) {
                continue;
            }

            List<Enrollment> roster = section.getRoster();
            for (Enrollment e : roster) {
                if (e.getStudent().getId().equals(studentId)) {
                    result.add(section);
                    break;
                }
            }
        }

        return result;
    }
}

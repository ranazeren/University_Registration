package edu.university.registration.ui;

import edu.university.registration.model.course.Course;
import edu.university.registration.model.course.Section;
import edu.university.registration.model.person.Instructor;
import edu.university.registration.model.person.Student;
import edu.university.registration.repository.*;
import edu.university.registration.service.admin.AdminService;
import edu.university.registration.service.admin.AdminServiceImpl;
import edu.university.registration.service.catalog.CatalogService;
import edu.university.registration.service.catalog.CatalogServiceImpl;
import edu.university.registration.service.grading.GradingService;
import edu.university.registration.service.grading.GradingServiceImpl;
import edu.university.registration.service.registration.RegistrationService;
import edu.university.registration.service.registration.RegistrationServiceImpl;
import edu.university.registration.service.validation.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // === REPOSITORIES ===
        Repository<Course, String> courseRepo = new InMemoryCourseRepository();
        Repository<Section, String> sectionRepo = new InMemorySectionRepository();
        Repository<Student, String> studentRepo = new InMemoryStudentRepository();
        Repository<Instructor, String> instructorRepo = new InMemoryInstructorRepository();

        // === VALIDATORS ===
        PrerequisiteValidator prereqValidator = new PrerequisiteValidator();
        CapacityValidator capacityValidator = new DefaultCapacityValidator();
        ScheduleConflictChecker conflictChecker = new DefaultScheduleConflictChecker();

        // === SERVICES ===
        CatalogService catalogService = new CatalogServiceImpl(courseRepo, sectionRepo);
        RegistrationService registrationService = new RegistrationServiceImpl(
                studentRepo, sectionRepo, prereqValidator, capacityValidator, conflictChecker
        );
        GradingService gradingService = new GradingServiceImpl(studentRepo, sectionRepo);
        AdminService adminService = new AdminServiceImpl(courseRepo, sectionRepo, instructorRepo);

        // === UI LAYER OBJECTS ===
        AdminConsole adminUI = new AdminConsole(studentRepo, courseRepo, sectionRepo, instructorRepo,
                catalogService, adminService);
        StudentConsole studentUI = new StudentConsole(studentRepo, registrationService, catalogService);
        InstructorConsole instructorUI = new InstructorConsole(gradingService, catalogService, sectionRepo);

        Scanner scanner = new Scanner(System.in);

        // === ROLE SELECTION LOOP ===
        while (true) {
            System.out.println("\n=== University Registration System ===");
            System.out.println("Select role:");
            System.out.println("1) Admin");
            System.out.println("2) Student");
            System.out.println("3) Instructor");
            System.out.println("0) Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> adminUI.run(scanner);
                case "2" -> studentUI.run(scanner);
                case "3" -> instructorUI.run(scanner);
                case "0" -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Unknown choice.");
            }
        }
    }
}

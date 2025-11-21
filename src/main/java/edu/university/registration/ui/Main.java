package edu.university.registration.ui;

import edu.university.registration.model.course.Course;
import edu.university.registration.model.course.Section;
import edu.university.registration.model.course.TimeSlot;
import edu.university.registration.model.enrollment.Grade;
import edu.university.registration.model.person.Student;

import edu.university.registration.repository.*;

import edu.university.registration.service.catalog.CatalogService;
import edu.university.registration.service.catalog.CatalogServiceImpl;
import edu.university.registration.service.grading.GradingService;
import edu.university.registration.service.grading.GradingServiceImpl;
import edu.university.registration.service.registration.RegistrationService;
import edu.university.registration.service.registration.RegistrationServiceImpl;
import edu.university.registration.service.validation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // === REPOSITORIES ===
        Repository<Course, String> courseRepo = new InMemoryCourseRepository();
        Repository<Section, String> sectionRepo = new InMemorySectionRepository();
        Repository<Student, String> studentRepo = new InMemoryStudentRepository();

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

        Scanner scanner = new Scanner(System.in);

        // === MAIN MENU LOOP ===
        while (true) {
            System.out.println("\n=== University Registration System ===");

            System.out.println("[ADMIN]");
            System.out.println("1) Create student");
            System.out.println("2) Create course");
            System.out.println("3) Create section");

            System.out.println("\n[STUDENT]");
            System.out.println("4) Enroll student to section");
            System.out.println("6) Show student GPA");

            System.out.println("\n[INSTRUCTOR]");
            System.out.println("5) Assign grade");

            System.out.println("\n0) Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            if ("0".equals(choice)) {
                System.out.println("Goodbye!");
                break;
            }

            try {
                switch (choice) {

                    // === ADMIN ACTIONS ===
                    case "1" -> createStudent(scanner, studentRepo);
                    case "2" -> createCourse(scanner, catalogService);
                    case "3" -> createSection(scanner, catalogService, courseRepo);

                    // === STUDENT ACTIONS ===
                    case "4" -> enrollStudent(scanner, registrationService);
                    case "6" -> showGpa(scanner, studentRepo);

                    // === INSTRUCTOR ACTIONS ===
                    case "5" -> assignGrade(scanner, gradingService);

                    default -> System.out.println("Unknown choice.");
                }

            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }

        scanner.close();
    }

    // ==================== ADMIN FUNCTIONS ====================

    private static void createStudent(Scanner scanner, Repository<Student, String> repo) {
        System.out.println("\n ");

        System.out.print("ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Major: ");
        String major = scanner.nextLine().trim();

        Student s = new Student(id, name, email, major);
        repo.save(s);

        System.out.println("Student created.");
    }

    private static void createCourse(Scanner scanner, CatalogService catalogService) {
        System.out.println("\n ");

        System.out.print("Course code: ");
        String code = scanner.nextLine().trim();

        System.out.print("Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Credits: ");
        int credits = Integer.parseInt(scanner.nextLine().trim());

        Course c = catalogService.createCourse(code, title, credits);

        if (c == null) System.out.println("Error creating course.");
        else System.out.println("Course created: " + c.getCode());
    }

    private static void createSection(Scanner scanner, CatalogService catalogService, Repository<Course, String> courseRepo) {

        System.out.println("\n ");

        System.out.print("Section id: ");
        String id = scanner.nextLine().trim();

        System.out.print("Course code: ");
        String courseCode = scanner.nextLine().trim();

        Course course = courseRepo.findById(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.print("Term: ");
        String term = scanner.nextLine().trim();

        System.out.print("Capacity: ");
        int capacity = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("Enter TimeSlot:");
        System.out.print("Day of week : ");
        DayOfWeek day = DayOfWeek.of(Integer.parseInt(scanner.nextLine().trim()));

        System.out.print("Start : ");
        LocalTime start = LocalTime.parse(scanner.nextLine().trim());

        System.out.print("End : ");
        LocalTime end = LocalTime.parse(scanner.nextLine().trim());

        System.out.print("Room: ");
        String room = scanner.nextLine().trim();

        TimeSlot ts = new TimeSlot(day, start, end, room);
        List<TimeSlot> slots = new ArrayList<>();
        slots.add(ts);

        Section section = catalogService.createSection(id, courseCode, term, capacity, slots);

        if (section == null) System.out.println("Could not create section.");
        else System.out.println("Section created: " + section.getId());
    }

    // ==================== STUDENT FUNCTIONS ====================

    private static void enrollStudent(Scanner scanner, RegistrationService registrationService) {
        System.out.println("\n ");

        System.out.print("Student id: ");
        String sid = scanner.nextLine().trim();

        System.out.print("Section id: ");
        String sec = scanner.nextLine().trim();

        boolean ok = registrationService.enroll(sid, sec);

        System.out.println(ok ? "Enrollment successful." : "Enrollment failed.");
    }

    private static void showGpa(Scanner scanner, Repository<Student, String> repo) {
        System.out.println("\n ");

        System.out.print("Student id: ");
        String id = scanner.nextLine().trim();

        Student s = repo.findById(id);

        if (s == null) System.out.println("Student not found.");
        else System.out.println("GPA: " + s.getGpa());
    }

    // ==================== INSTRUCTOR FUNCTIONS ====================

    private static void assignGrade(Scanner scanner, GradingService gradingService) {
        System.out.println("\n--- Assign Grade (Instructor) ---");

        System.out.print("Instructor id: ");
        String inst = scanner.nextLine().trim();
        if (inst.isBlank()) inst = null;

        System.out.print("Section id: ");
        String sec = scanner.nextLine().trim();

        System.out.print("Student id: ");
        String stu = scanner.nextLine().trim();

        System.out.print("Grade  ");
        String g = scanner.nextLine().trim().toUpperCase();

        Grade grade;
        try {
            grade = Grade.valueOf(g);
        } catch (Exception ex) {
            System.out.println("Invalid grade.");
            return;
        }

        gradingService.assignGrade(inst, sec, stu, grade);
        System.out.println("Grade assigned.");
    }
}

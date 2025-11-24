package edu.university.registration.ui;

import edu.university.registration.model.course.Course;
import edu.university.registration.model.course.Section;
import edu.university.registration.model.course.TimeSlot;
import edu.university.registration.model.person.Instructor;
import edu.university.registration.model.person.Student;
import edu.university.registration.repository.Repository;
import edu.university.registration.service.admin.AdminService;
import edu.university.registration.service.catalog.CatalogService;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminConsole {

    private final Repository<Student, String> studentRepo;
    private final Repository<Course, String> courseRepo;
    private final Repository<Section, String> sectionRepo;
    private final Repository<Instructor, String> instructorRepo;
    private final CatalogService catalogService;
    private final AdminService adminService;

    public AdminConsole(Repository<Student, String> studentRepo,
                        Repository<Course, String> courseRepo,
                        Repository<Section, String> sectionRepo,
                        Repository<Instructor, String> instructorRepo,
                        CatalogService catalogService,
                        AdminService adminService) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.sectionRepo = sectionRepo;
        this.instructorRepo = instructorRepo;
        this.catalogService = catalogService;
        this.adminService = adminService;
    }

    public void run(Scanner scanner) {
        while (true) {
            System.out.println("\n----ADMIN MENU -----");
            System.out.println("1) Create student");
            System.out.println("2) Create course");
            System.out.println("3) Create section");
            System.out.println("0) Back");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> createStudent(scanner);
                    case "2" -> createCourse(scanner);
                    case "3" -> createSection(scanner);
                    case "0" -> { return; }
                    default -> System.out.println("Unknown choice.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private void createStudent(Scanner scanner) {
        System.out.println("\n--- Create Student ---");

        System.out.print("ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Major: ");
        String major = scanner.nextLine().trim();

        Student s = new Student(id, name, email, major);
        studentRepo.save(s);

        System.out.println("Student created.");
    }

    private void createCourse(Scanner scanner) {
        System.out.println("\n--- Create Course ---");

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

    private void createSection(Scanner scanner) {
        System.out.println("\n--- Create Section ---");

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
        System.out.print("Day of week (1=MON ... 7=SUN): ");
        DayOfWeek day = DayOfWeek.of(Integer.parseInt(scanner.nextLine().trim()));

        System.out.print("Start (HH:MM): ");
        LocalTime start = LocalTime.parse(scanner.nextLine().trim());

        System.out.print("End (HH:MM): ");
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
}

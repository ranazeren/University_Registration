package edu.university.registration.ui;

import edu.university.registration.model.course.Course;
import edu.university.registration.model.course.Section;
import edu.university.registration.model.course.TimeSlot;
import edu.university.registration.model.person.Student;
import edu.university.registration.model.transcript.TranscriptEntry;
import edu.university.registration.repository.Repository;
import edu.university.registration.service.catalog.CatalogService;
import edu.university.registration.service.registration.RegistrationService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class StudentConsole {

    private final Repository<Student, String> studentRepo;
    private final RegistrationService registrationService;
    private final CatalogService catalogService;

    public StudentConsole(Repository<Student, String> studentRepo,
                          RegistrationService registrationService,
                          CatalogService catalogService) {
        this.studentRepo = studentRepo;
        this.registrationService = registrationService;
        this.catalogService = catalogService;
    }

    public void run(Scanner scanner) {
        while (true) {
            System.out.println("\n=== STUDENT MENU ===");
            System.out.println("1) Enroll to section");
            System.out.println("2) Show GPA");
            System.out.println("3) Drop section");
            System.out.println("4) Search courses");
            System.out.println("5) View my schedule");
            System.out.println("6) View transcript");
            System.out.println("0) Back");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> enrollStudent(scanner);
                    case "2" -> showGpa(scanner);
                    case "3" -> dropSection(scanner);
                    case "4" -> searchCourses(scanner);
                    case "5" -> viewSchedule(scanner);
                    case "6" -> viewTranscript(scanner);
                    case "0" -> { return; }
                    default -> System.out.println("Unknown choice.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }


    private void enrollStudent(Scanner scanner) {
        System.out.println("\n--- Enroll Student ---");

        System.out.print("Student id: ");
        String sid = scanner.nextLine().trim();

        System.out.print("Section id: ");
        String sec = scanner.nextLine().trim();

        boolean ok = registrationService.enroll(sid, sec);

        System.out.println(ok ? "Enrollment successful." : "Enrollment failed.");
    }


    private void showGpa(Scanner scanner) {
        System.out.println("\n--- Show GPA ---");

        System.out.print("Student id: ");
        String id = scanner.nextLine().trim();

        Student s = studentRepo.findById(id);

        if (s == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println("GPA: " + s.getGpa());
        }
    }


    private void dropSection(Scanner scanner) {
        System.out.println("\n--- Drop Section ---");

        System.out.print("Student id: ");
        String sid = scanner.nextLine().trim();

        System.out.print("Section id: ");
        String sec = scanner.nextLine().trim();

        boolean ok = registrationService.drop(sid, sec);

        System.out.println(ok ? "Drop successful." : "Drop failed.");
    }

    private void searchCourses(Scanner scanner) {
        System.out.println("\n--- Search Courses ---");

        System.out.print("Keyword (code or title): ");
        String keyword = scanner.nextLine().trim();

        List<Course> courses = catalogService.search(keyword);

        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        System.out.println("Found courses:");
        for (Course c : courses) {
            System.out.println("- " + c.getCode() + " : " + c.getTitle()
                    + " (" + c.getCredits() + " credits)");
        }
    }


    private void viewSchedule(Scanner scanner) {
        System.out.println("\n--- View Schedule ---");

        System.out.print("Student id: ");
        String sid = scanner.nextLine().trim();

        System.out.print("Term (e.g. 2025FALL): ");
        String term = scanner.nextLine().trim();

        List<Section> sections = registrationService.listSchedule(sid, term);

        if (sections.isEmpty()) {
            System.out.println("No sections for this student/term.");
            return;
        }

        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        for (Section s : sections) {
            System.out.println("\nSection: " + s.getId()
                    + " | Course: " + s.getCourse().getCode()
                    + " - " + s.getCourse().getTitle());

            for (TimeSlot ts : s.getMeetingTimes()) {
                System.out.println("  " + ts.getDayOfWeek()
                        + " " + ts.getStart().format(timeFmt)
                        + "-" + ts.getEnd().format(timeFmt)
                        + " Room: " + ts.getRoom());
            }
        }
    }


    private void viewTranscript(Scanner scanner) {
        System.out.println("\n--- View Transcript ---");

        System.out.print("Student id: ");
        String sid = scanner.nextLine().trim();

        Student s = studentRepo.findById(sid);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        var transcript = s.getTranscript();
        var entries = transcript.getEntries();

        if (entries.isEmpty()) {
            System.out.println("Transcript is empty.");
            return;
        }

        System.out.println("Transcript for " + s.getName() + " (" + s.getId() + "):");
        for (TranscriptEntry e : entries) {
            System.out.println("- " + e.getCourseCode()
                    + " | credits: " + e.getCredits()
                    + " | grade: " + e.getGrade());
        }
        System.out.println("Current GPA: " + s.getGpa());
    }
}

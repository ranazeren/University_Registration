package edu.university.registration.ui;

import edu.university.registration.model.course.Section;
import edu.university.registration.model.enrollment.Grade;
import edu.university.registration.repository.Repository;
import edu.university.registration.service.catalog.CatalogService;
import edu.university.registration.service.grading.GradingService;

import java.util.Scanner;

public class InstructorConsole {

    private final GradingService gradingService;
    private final CatalogService catalogService;
    private final Repository<Section, String> sectionRepo;

    public InstructorConsole(GradingService gradingService,
                             CatalogService catalogService,
                             Repository<Section, String> sectionRepo) {
        this.gradingService = gradingService;
        this.catalogService = catalogService;
        this.sectionRepo = sectionRepo;
    }

    public void run(Scanner scanner) {
        while (true) {
            System.out.println("\n=== INSTRUCTOR MENU ===");
            System.out.println("1) Assign grade");
            System.out.println("0) Back");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> assignGrade(scanner);
                    case "0" -> { return; }
                    default -> System.out.println("Unknown choice.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private void assignGrade(Scanner scanner) {
        System.out.println("\n--- Assign Grade ---");

        System.out.print("Instructor id: ");
        String inst = scanner.nextLine().trim();
        if (inst.isBlank()) inst = null;

        System.out.print("Section id: ");
        String sec = scanner.nextLine().trim();

        System.out.print("Student id: ");
        String stu = scanner.nextLine().trim();

        System.out.print("Grade (A,B,C,D,F,I,W): ");
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

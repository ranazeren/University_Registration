package edu.university.registration.model.transcript;

import edu.university.registration.model.enrollment.Grade;

import java.util.ArrayList;
import java.util.List;

public class Transcript {

    private final List<TranscriptEntry> entries = new ArrayList<>();

    public Transcript() {
    }

    public void addEntry(TranscriptEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Transcript entry cannot be null");
        }
        entries.add(entry);
    }

    public List<TranscriptEntry> getEntries() {
        return List.copyOf(entries);
    }

    public double computeGpa() {
        double totalPoints = 0.0;
        int totalCredits = 0;

        for (TranscriptEntry entry : entries) {
            Grade grade = entry.getGrade();


            if (grade == null || !grade.countsInGpa()) {
                continue;
            }

            int credits = entry.getCredits();
            if (credits <= 0) {
                throw new IllegalStateException("Transcript entry credits must be positive");
            }

            totalPoints += grade.points() * credits;
            totalCredits += credits;
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        return totalPoints / totalCredits;
    }
}

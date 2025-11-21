package edu.university.registration.model.transcript;

import edu.university.registration.model.enrollment.Grade;

import java.util.ArrayList;
import java.util.List;

public class Transcript {

    private List<TranscriptEntry> entries = new ArrayList<>();

    public Transcript() {
    }

    public void addEntry(TranscriptEntry entry) {
        if (entry == null) return;
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

            if (grade == null) continue;
            if (!grade.countsInGpa()) continue;

            double gp = grade.points();
            int cr = entry.getCredits();

            totalPoints += gp * cr;
            totalCredits += cr;
        }

        if (totalCredits == 0) return 0.0;

        return totalPoints / totalCredits;
    }
}

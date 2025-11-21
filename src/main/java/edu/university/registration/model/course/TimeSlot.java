package edu.university.registration.model.course;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public final class TimeSlot implements Comparable<TimeSlot> {
    private final DayOfWeek dayOfWeek;
    private final LocalTime start;
    private final LocalTime end;
    private final String room;

    public TimeSlot(DayOfWeek dayOfWeek, LocalTime start, LocalTime end, String room) {
        this.dayOfWeek = Objects.requireNonNull(dayOfWeek, "dayOfWeek");
        this.start = Objects.requireNonNull(start, "start");
        this.end = Objects.requireNonNull(end, "end");
        if (!this.start.isBefore(this.end)) {
            throw new IllegalArgumentException("start must be before end");
        }
        this.room = (room == null) ? "" : room;
    }

    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public LocalTime getStart() { return start; }
    public LocalTime getEnd() { return end; }
    public String getRoom() { return room; }


    public boolean overlaps(TimeSlot other) {
        if (this.dayOfWeek != other.dayOfWeek) return false;
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }

    @Override
    public int compareTo(TimeSlot o) {
        int byDay = this.dayOfWeek.compareTo(o.dayOfWeek);
        if (byDay != 0) return byDay;
        int byStart = this.start.compareTo(o.start);
        if (byStart != 0) return byStart;
        return this.end.compareTo(o.end);
    }
}

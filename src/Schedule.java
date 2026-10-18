package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Schedule {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private ArrayList<DayOfWeek> workdays;

    public Schedule(LocalDate startDate, LocalDate endDate, LocalTime startTime,  LocalTime endTime, ArrayList<DayOfWeek> workdays)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workdays = workdays;
    }

    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public LocalTime getStartTime() {return startTime;}
    public LocalTime getEndTime() {return endTime;}
    public ArrayList<DayOfWeek> getWorkdays() {return workdays;}

    public boolean contains(LocalDateTime now) {
        return now.toLocalDate().isAfter(startDate) && now.toLocalDate().isBefore(endDate) && now.toLocalTime().isAfter(startTime) && now.toLocalTime().isBefore(endTime) && workdays.contains(now.getDayOfWeek());
    }
}

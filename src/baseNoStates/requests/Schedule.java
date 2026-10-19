package baseNoStates.requests;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Schedule {    // Class that represents a user's work schedule. It consists of a day range, hour range and available days of the week.
                          // It works with RequestReader to see if the LocalDateTime varible is "off schedule".
    private LocalDate startDate;    //Day range start
    private LocalDate endDate;      //Day range end
    private LocalTime startTime;    //Hour range start
    private LocalTime endTime;      //Hour range end
    private ArrayList<DayOfWeek> workdays;  //ArrayList of the user's workdays

    public Schedule(LocalDate startDate, LocalDate endDate, LocalTime startTime,  LocalTime endTime, ArrayList<DayOfWeek> workdays)   //Class constructor. It creates a schedule based on input.
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workdays = workdays;
    }

    //Class getters
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public LocalTime getStartTime() {return startTime;}
    public LocalTime getEndTime() {return endTime;}
    public ArrayList<DayOfWeek> getWorkdays() {return workdays;}

    public boolean contains(LocalDateTime now) {    //Checks if a date is contained in a schedule.
        return now.toLocalDate().isAfter(startDate) && now.toLocalDate().isBefore(endDate) && now.toLocalTime().isAfter(startTime) && now.toLocalTime().isBefore(endTime) && workdays.contains(now.getDayOfWeek());
    }
}

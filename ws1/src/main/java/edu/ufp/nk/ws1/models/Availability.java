package edu.ufp.nk.ws1.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
public class Availability extends BaseModel {
    //Variables
    private LocalTime start;
    private LocalTime end;
    private int day_of_week;

    //Constructor
    public Availability(LocalTime start, LocalTime end, int day_of_week){
        this.start =start;
        this.end = end;
        this.day_of_week= day_of_week;
    }

    //Gets & Sets
    public void setStart(LocalTime start) {this.start=start;}
    public LocalTime getStart(){return start;}
    public void setEnd(LocalTime end){this.end=end;}
    public LocalTime getEnd(){return end;}
    public void setDay_of_week(int day_of_week){this.day_of_week=day_of_week;}
    public int getDay_of_week(){return day_of_week;}
}

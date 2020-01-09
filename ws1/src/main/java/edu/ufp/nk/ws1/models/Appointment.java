package edu.ufp.nk.ws1.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
public class Appointment extends BaseModel {
    // Variables
    private LocalDate date;
    private LocalTime start;

    public Appointment(){
    }

    // Constructor
    public Appointment(LocalDate date, LocalTime start){
        this.date = date; this.start=start;
    }


    // Gets & Sets
    public void setDate (LocalDate date) {this.date = date;}
    public LocalDate getDate(){return date;}
    public void setStart(LocalTime start) {this.start = start;}
    public LocalTime getStart(){return start;}

}

package edu.ufp.nk.ws1.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
public class Availability extends BaseModel {
    //Variables
    private LocalTime start;
    private LocalTime end;
    private int dayOfWeek;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Explainer explainer;


    //Constructor
    public Availability(LocalTime start, LocalTime end, int dayOfWeek){
        this.start =start;
        this.end = end;
        this.dayOfWeek = dayOfWeek;
    }

    //Gets & Sets
    public void setStart(LocalTime start) {this.start=start;}
    public LocalTime getStart(){return start;}
    public void setEnd(LocalTime end){this.end=end;}
    public LocalTime getEnd(){return end;}
    public void setDayOfWeek(int dayOfWeek){this.dayOfWeek = dayOfWeek;}
    public int getDayOfWeek(){return dayOfWeek;}
}

package edu.ufp.nk.ws1.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
public class Appointment extends BaseModel {
    // Variables
    private LocalDate date;
    private LocalTime start;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Student student;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Explainer explainer;

    // Constructor
    public Appointment(LocalDate date, LocalTime start, Student student, Explainer explainer) {
        this.date = date;
        this.start = start;
        this.student = student;
        this.explainer = explainer;
    }


    // Gets & Sets
    public void setDate (LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setStart(LocalTime start) {this.start = start;}
    public LocalTime getStart(){return start;}

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Explainer getExplainer() {
        return explainer;
    }

    public void setExplainer(Explainer explainer) {
        this.explainer = explainer;
    }
}

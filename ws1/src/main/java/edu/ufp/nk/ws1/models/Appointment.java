package edu.ufp.nk.ws1.models;

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
}

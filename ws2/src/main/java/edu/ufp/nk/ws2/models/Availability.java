package edu.ufp.nk.ws2.models;

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
public class Availability extends BaseModel {
    //Variables
    private LocalTime start;
    private LocalTime end;
    private LocalDate day;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Explainer explainer;

    //Constructor
    public Availability(Explainer explainer, LocalTime start, LocalTime end, LocalDate day) {
        this.explainer = explainer;
        this.start = start;
        this.end = end;
        this.day = day;
    }
}

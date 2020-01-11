package edu.ufp.nk.ws1.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Explainer extends BaseModel {
    //Variables
    private String name;

    @OneToOne
    private Degree degree;

    @OneToMany(mappedBy = "explainer", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<Availability> availabilities = new HashSet<>();
/*
    @OneToMany(mappedBy = "explainer", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<Appointment> appointments = new HashSet<>();
*/

    //Constructor
    public Explainer (String name) { this.name = name; }

    //Gets & Sets
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Set<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(Set<Availability> availabilities) {
        this.availabilities = availabilities;
    }
/*
    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
*/
}

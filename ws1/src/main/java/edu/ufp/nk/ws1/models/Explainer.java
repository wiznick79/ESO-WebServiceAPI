package edu.ufp.nk.ws1.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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


}

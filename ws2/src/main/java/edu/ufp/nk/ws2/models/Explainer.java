package edu.ufp.nk.ws2.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
public class Explainer extends BaseModel{
    private String name;

    @OneToOne
    private Degree degree;

    @OneToMany(mappedBy = "explainer", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<Availability> availabilities = new HashSet<>();

    @OneToMany
    private Set<Language> languages = new HashSet<>();

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private University university;


    public Explainer(String name){this.name=name;}
}

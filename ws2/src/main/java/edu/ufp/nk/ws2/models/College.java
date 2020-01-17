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

@Entity
@Data
@NoArgsConstructor
public class College extends BaseModel {


    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "college", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<Degree> degrees = new HashSet<>();

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private University university;


    public College(String name) {
        this.name = name;
    }
}

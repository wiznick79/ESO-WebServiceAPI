package edu.ufp.nk.ws2.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Entity
@NoArgsConstructor
public class Course extends BaseModel{
    // Variables
    private String name;

    @OneToOne
    private Degree degree;

    // Constructor
    public Course(String name) {
        this.name = name;
    }

}

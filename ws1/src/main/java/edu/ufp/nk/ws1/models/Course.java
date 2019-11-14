package edu.ufp.nk.ws1.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


@Data
@Entity
@NoArgsConstructor
public class Course extends BaseModel {

    // Variables
    private String name;

    @OneToOne
    private Degree degree;

    // Constructor
    public Course(String name){
        this.name = name;
    }


    // Gets & Sets
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    public Degree getDegree(){
        return degree;
    }
    public void setDegree(Degree degree) {
        this.degree = degree;
    }


}
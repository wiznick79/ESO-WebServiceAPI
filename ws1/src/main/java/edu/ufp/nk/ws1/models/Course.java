package edu.ufp.nk.ws1.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
public class Course extends BaseModel {
    // Variables
    private String name;


    // Constructor
    public Course(String name){
        this.name = name;
    }


    // Gets & Sets
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

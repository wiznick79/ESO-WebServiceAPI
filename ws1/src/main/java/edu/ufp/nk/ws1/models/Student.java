package edu.ufp.nk.ws1.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
public class Student extends BaseModel {
    //Variables
    private String name;
    @Column(unique = true)
    private int studentNumber;

    //Constructor
    public Student(String name, int studentNumber){
        this.name = name;
        this.studentNumber = studentNumber;
    }


}

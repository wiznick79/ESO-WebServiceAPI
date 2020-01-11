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

    //Gets & Sets
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getStudentNumber() { return studentNumber; }
    public void setStudentNumber(int studentNumber) { this.studentNumber = studentNumber; }

}

package edu.ufp.nk.ws1.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
public class Student extends BaseModel {
    //Variables
    private String name;
    private int student_number;

    //Constructor
    public Student(String Name, int student_number){
        this.name=name;
        this.student_number=student_number;
    }

    //Gets & Sets
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getStudent_number() {return student_number;}
    public void setStudent_number(int student_number) {this.student_number = student_number;}
}

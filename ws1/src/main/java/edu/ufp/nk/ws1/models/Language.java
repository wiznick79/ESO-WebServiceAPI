package edu.ufp.nk.ws1.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
public class Language extends BaseModel {
    //Variables
    private String name;

    //Constructor
    public Language(String name){
        this.name=name;
    }

}

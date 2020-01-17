package edu.ufp.nk.ws2.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class University extends BaseModel{

    @Column(unique = true)
    private String name;
    private String ip;

    @OneToMany(mappedBy = "university", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<College> colleges = new HashSet<>();

    @OneToMany(mappedBy = "university", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<Explainer> explainers = new HashSet<>();




    public void addCollege(College college){
        this.colleges.add(college);
        college.setUniversity(this);
    }

    public University(String name, String ip){
        this.name = name;
        this.ip = ip;
    }
}

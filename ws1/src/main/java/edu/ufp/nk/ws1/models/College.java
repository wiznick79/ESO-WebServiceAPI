package edu.ufp.nk.ws1.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class College extends BaseModel {

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "college", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<Degree> degrees = new HashSet<>();

    public College(String name) {
        this.name = name;
    }
}

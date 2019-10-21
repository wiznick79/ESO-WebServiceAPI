package edu.ufp.nk.ws1.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;


@Data
@Entity
@NoArgsConstructor
public class Degree extends BaseModel {

	// Variables
	private String name;


	// Constructor
	public Degree(String name){
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

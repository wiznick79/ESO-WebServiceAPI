package edu.ufp.nk.ws1.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
public class Degree extends BaseModel {

	// Variables
	@Column(unique = true)
	private String name;

	// Constructor
	public Degree(String name){
		this.name = name;
	}

	// Gets & Sets
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

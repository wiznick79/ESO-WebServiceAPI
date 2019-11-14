package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.repositories.DegreeRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@Controller
@RequestMapping("/degree")
public class DegreeController {
	private DegreeRepo degreeRepo;

	// Constructor
	public DegreeController (DegreeRepo degreeRepo){
		this.degreeRepo = degreeRepo;
	}


	@GetMapping
	public ResponseEntity<Iterable<Degree>> getAllDegrees(){
		return ResponseEntity.ok(this.degreeRepo.findAll());
	}

	@GetMapping("/id={id}")
	public ResponseEntity<Degree> searchDegree(@PathVariable long id){

		Degree found = degreeRepo.findById(id);

		if(found == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(found);
	}

	@PostMapping
	// TODO: Use code 201: Created
	// TODO: Change return code when already exists.
	public ResponseEntity<Degree> addDegree(@Valid @RequestBody Degree novo){
		if(degreeRepo.findByName(novo.getName()) != null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(degreeRepo.save(novo));
	}


}
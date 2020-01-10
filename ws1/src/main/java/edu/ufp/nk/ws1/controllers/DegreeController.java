package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.College;
import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.services.CollegeService;
import edu.ufp.nk.ws1.services.DegreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/degree")
public class DegreeController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private DegreeService degreeService;
	private CollegeService collegeService;

	// Constructor
	@Autowired
	public DegreeController(DegreeService degreeService, CollegeService collegeService) {
		this.degreeService = degreeService;
		this.collegeService = collegeService;
	}


	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Degree>> getAllDegrees()	{
		this.logger.info("Received a get request");

		return ResponseEntity.ok(this.degreeService.findAll());
	}

	@RequestMapping(value = "/id={id}", method = RequestMethod.GET)
	public ResponseEntity<Degree> getDegreeById(@PathVariable("id") long id) throws NoDegreeException {
		this.logger.info("Received a get request");

		Optional<Degree> optionalDegree = this.degreeService.findById(id);
		if (optionalDegree.isPresent()) {
			return ResponseEntity.ok(optionalDegree.get());
		}
		throw new NoDegreeException(id);
	}

	@PostMapping(value = "/{college}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Degree> createDegreeByCollege(@RequestBody Degree degree, @PathVariable Long college) {
		Optional<Degree> degreeOptional = this.degreeService.createDegreeByCollege(degree, college);
		if(degreeOptional.isPresent()) {
			return ResponseEntity.ok(degreeOptional.get());
		}
		Optional<College> collegeOptional = collegeService.findById(college);
		if (collegeOptional.isPresent()) {
			throw new DegreeAlreadyExistsException(degree.getName());
		}
		else throw new NoCollegeException(college);
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such degree")
	private static class NoDegreeException extends RuntimeException {
		public NoDegreeException(Long id) {
			super("No such degree with id: " + id);
		}
	}

	@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "Degree already exists")
	private static class DegreeAlreadyExistsException extends RuntimeException {
		public DegreeAlreadyExistsException(String name) {
			super("A degree with name: "+name+" already exists");
		}
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such college")
	private static class NoCollegeException extends RuntimeException {
		public NoCollegeException(Long id) {
			super("No such college with id: " + id);
		}
	}
}

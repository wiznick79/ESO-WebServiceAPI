package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Course;
import edu.ufp.nk.ws1.repositories.CourseRepo;
import edu.ufp.nk.ws1.services.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/course")
public class CourseController {
	private CourseRepo courseRepo;
	private CourseService courseService;

	// Constructor
	public CourseController(CourseRepo courseRepo){
		this.courseRepo = courseRepo;
	}


	@GetMapping
	public ResponseEntity<Iterable<Course>> getAllCourses(){
		return ResponseEntity.ok(this.courseRepo.findAll());
	}

	@GetMapping("/id={id}")
	public ResponseEntity<Course> searchCourse(@PathVariable long id){
		Course found = courseRepo.findById(id);

		if(found == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(found);
	}

	@PostMapping
	// TODO: Use code 201: Created
	// TODO: Change return code when already exists.
	public ResponseEntity<Course> addCourse(@Valid @RequestBody Course novo){
		if(courseRepo.findByName(novo.getName()) != null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(courseRepo.save(novo));
	}


}

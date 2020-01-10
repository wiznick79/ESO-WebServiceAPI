package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Course;
import edu.ufp.nk.ws1.services.CourseService;
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
@RequestMapping("/course")
public class CourseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private CourseService courseService;

	// Constructor
	@Autowired
	public CourseController(CourseService courseService){

		this.courseService = courseService;
	}


	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Course>> getAllCourses()	{
		this.logger.info("Received a get request");

		return ResponseEntity.ok(this.courseService.findAll());
	}

	@RequestMapping(value = "/id={id}", method = RequestMethod.GET)
	public ResponseEntity<Course> getCourseById(@PathVariable("id") long id) throws NoCourseException {
		this.logger.info("Received a get request");

		Optional<Course> optionalCourse = this.courseService.findById(id);
		if (optionalCourse.isPresent()){
			return ResponseEntity.ok(optionalCourse.get());
		}
		throw new NoCourseException(id);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	// TODO: Use code 201: Created
	// TODO: Change return code when already exists.
	public ResponseEntity<Course> createCourse(@RequestBody Course course){
		Optional<Course> courseOptional = this.courseService.createCourse(course);
		if(courseOptional.isPresent()) {
			return ResponseEntity.ok(courseOptional.get());
		}
		throw new CourseAlreadyExistsException(course.getName());
	}

	@PostMapping(value="/{degree}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Course> createCourseByDegree(@RequestBody Course course, @PathVariable Long degree){
		Optional<Course> courseOptional = this.courseService.createCourseByDegree(course, degree);
		if(courseOptional.isPresent()) {
			return ResponseEntity.ok(courseOptional.get());
		}
		throw new CourseAlreadyExistsException(course.getName());
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such course")
	private static class NoCourseException extends RuntimeException {
		public NoCourseException(Long id) {
			super("No such course with id: " + id);
		}
	}

	@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Course already exists")
	private static class CourseAlreadyExistsException extends RuntimeException {

		public CourseAlreadyExistsException(String name) {
			super("A course with name: "+name+" already exists");
		}
	}

}

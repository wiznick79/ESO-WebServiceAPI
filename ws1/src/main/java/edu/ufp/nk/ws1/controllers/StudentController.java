package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Student;
import edu.ufp.nk.ws1.services.StudentService;
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
@RequestMapping("/student")
public class StudentController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private StudentService studentService;

    //Constructor
    @Autowired
    private StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Student>> getAllStudents(){
        this.logger.info("Received a get request");

        return ResponseEntity.ok(this.studentService.findAll());
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentById(@PathVariable("id") long id) throws NoStudentException{
        this.logger.info("Received a get request");

        Optional<Student> optionalStudent=this.studentService.findById(id);
        if(optionalStudent.isPresent()){
            return ResponseEntity.ok(optionalStudent.get());
        }
        throw new NoStudentException(id);
    }

    @RequestMapping(value = "/number={number}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentByStudentNumber(@PathVariable("number") int number) throws NoStudentNumberException{
        this.logger.info("Received a get request");

        Optional<Student> optionalStudent=this.studentService.findByNumber(number);
        if(optionalStudent.isPresent()){
            return ResponseEntity.ok(optionalStudent.get());
        }
        throw new NoStudentNumberException(number);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    // TODO: Use code 201: Created
    // TODO: Change return code when already exists.
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Optional<Student> studentOptional = this.studentService.createStudent(student);
        if (studentOptional.isPresent()){
            return ResponseEntity.ok(studentOptional.get());
        }
        throw new StudentAlreadyExistsException(student.getStudentNumber());
    }


    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such student")
    private static class NoStudentException extends RuntimeException {
        public NoStudentException(Long id) {
            super("No such student with id: "+id);
        }
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such student")
    private static class NoStudentNumberException extends RuntimeException {
        public NoStudentNumberException(int number) {
            super("No such student with number: "+number);
        }
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Student already exists")
    private static class StudentAlreadyExistsException extends RuntimeException {
        public StudentAlreadyExistsException(int number) {
            super("A student with number: "+number+" already exists");
        }
    }



}

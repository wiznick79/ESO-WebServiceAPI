package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Student;
import edu.ufp.nk.ws1.repositories.StudentRepo;
import edu.ufp.nk.ws1.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/student")
public class StudentController {
    private StudentRepo studentRepo;
    private StudentService studentService;

    //Constructor
    public StudentController(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @GetMapping
    public ResponseEntity<Iterable<Student>> getAllStudents(){
        return ResponseEntity.ok(this.studentRepo.findAll());
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Student> searchStudent(@PathVariable long id){
        Student found = studentRepo.findById(id);

        if(found == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(found);
    }

    @PostMapping
    // TODO: Use code 201: Created
    // TODO: Change return code when already exists.
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student novo){
        if(studentRepo.findByStudent_number(novo.getStudent_number()) != null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(studentRepo.save(novo));
    }
}

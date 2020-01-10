package edu.ufp.nk.ws2.controllers;

import edu.ufp.nk.ws2.models.University;
import edu.ufp.nk.ws2.services.UniversityService;
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
@RequestMapping("/university")
public class UniversityController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService){
        this.universityService = universityService;
    }



    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<University>> getAllUniversities(){
        this.logger.info("Received a get request");

        return ResponseEntity.ok(this.universityService.findAll());
    }

    @RequestMapping(value = "/id={id}",method = RequestMethod.GET)
    public ResponseEntity<University> getUniversityById(@PathVariable("id") Long id) throws NoUniversityException {
        this.logger.info("Received a get request");

        Optional<University> optionalUniversity=this.universityService.findById(id);
        if(optionalUniversity.isPresent()) {
            return ResponseEntity.ok(optionalUniversity.get());
        }
        throw new NoUniversityException(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<University> createUniversity(@RequestBody University university){
        Optional<University> optionalUniversity =this.universityService.createUniversity(university);
        if(optionalUniversity.isPresent()){
            return ResponseEntity.ok(optionalUniversity.get());
        }
        throw new UniversityAlreadyExistsException(university.getName());

    }




    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such university")
    private static class NoUniversityException extends RuntimeException {

        public NoUniversityException(Long id) {
            super("No such university with id: "+id);
        }
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="University already exists")
    private static class UniversityAlreadyExistsException extends RuntimeException {

        public UniversityAlreadyExistsException(String name) {
            super("A university with name: "+name+" already exists");
        }
    }

}

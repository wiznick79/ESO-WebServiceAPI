package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.College;
import edu.ufp.nk.ws1.services.CollegeService;
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
@RequestMapping("/college")
public class CollegeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private CollegeService collegeService;

    @Autowired
    public CollegeController(CollegeService collegeService){
        this.collegeService = collegeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<College>> getAllColleges(){
        this.logger.info("Received a get request");

        return ResponseEntity.ok(this.collegeService.findAll());
    }

    @RequestMapping(value = "/id={id}",method = RequestMethod.GET)
    public ResponseEntity<College> getCollegeById(@PathVariable("id") Long id) throws NoCollegeExcpetion {
        this.logger.info("Received a get request");

        Optional<College> optionalCollege=this.collegeService.findById(id);
        if(optionalCollege.isPresent()) {
            return ResponseEntity.ok(optionalCollege.get());
        }
        throw new NoCollegeExcpetion(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<College> createCollege(@RequestBody College college){
        this.logger.info("Received a post request");

        Optional<College> collegeOptional=this.collegeService.createCollege(college);
        if(collegeOptional.isPresent()){
            return ResponseEntity.ok(collegeOptional.get());
        }
        throw new CollegeAlreadyExistsExcpetion(college.getName());

    }

    /*
     * EXCEPTIONS
     */

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such college")
    private static class NoCollegeExcpetion extends RuntimeException {

        public NoCollegeExcpetion(Long id) {
            super("No such college with id: "+id);
        }
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="College already exists")
    private static class CollegeAlreadyExistsExcpetion extends RuntimeException {

        public CollegeAlreadyExistsExcpetion(String name) {
            super("A college with name: "+name+" already exists");
        }
    }
}

package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.ExplainerService;
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
@RequestMapping("/explainer")
public class ExplainerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExplainerService explainerService;

    //Constructor
    @Autowired
    public ExplainerController(ExplainerService explainerService) {
        this.explainerService = explainerService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Explainer>> getAllExplainers(){
        this.logger.info("Received a get request");
        return ResponseEntity.ok(this.explainerService.findAll());
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET)
    public ResponseEntity<Explainer> getExplainerById(@PathVariable("id") long id) throws NoExplainerException{
        this.logger.info("Received a get request");
        Optional<Explainer> optionalExplainer = this.explainerService.findById(id);
        if(optionalExplainer.isPresent()){
            return ResponseEntity.ok(optionalExplainer.get());
        }
        throw new NoExplainerException();
    }

    @RequestMapping(value = "/name={name}", method = RequestMethod.GET)
    public ResponseEntity<Explainer> getExplainerByName(@PathVariable("name") String name) throws NoExplainerException{
        this.logger.info("Received a get request");
        Optional<Explainer> optionalExplainer = this.explainerService.findByName(name);
        if(optionalExplainer.isPresent()){
            return ResponseEntity.ok(optionalExplainer.get());
        }
        throw new NoExplainerException();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explainer> createExplainer(@RequestBody Explainer explainer){
        Optional<Explainer> explainerOptional = this.explainerService.createExplainer(explainer);
        if (explainerOptional.isPresent()){
            return ResponseEntity.ok(explainerOptional.get());
        }
        throw new ExplainerAlreadyExistsExcpetion(explainer.getName());
    }


    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explainer> addAvailabilitie(@PathVariable("id") Long id, @RequestBody Availability availability){

        Optional<Explainer> explainerOptional = this.explainerService.addAvailabilitie(id, availability);
        if (explainerOptional.isPresent()){
            return ResponseEntity.ok(explainerOptional.get());
        }

        return ResponseEntity.badRequest().build();
    }






    /*
     * EXCEPTIONS
     */

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such explainer")
    private static class NoExplainerException extends RuntimeException {
        public NoExplainerException() {
            super("No such explainer with");
        }
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Explainer already exists")
    private static class ExplainerAlreadyExistsExcpetion extends RuntimeException {
        public ExplainerAlreadyExistsExcpetion(String name) {
            super("An explainer with name: "+name+" already exists");
        }
    }
}

package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.repositories.ExplainerRepo;
import edu.ufp.nk.ws1.services.ExplainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        throw new NoExplainerException(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    // TODO: Use code 201: Created
    // TODO: Change return code when already exists.
    // TODO: Verificar o findByStartAndDate
    public ResponseEntity<Explainer> createExplainer(@RequestBody Explainer explainer){
        Optional<Explainer> explainerOptional = this.explainerService.createExplainer(explainer);
        if (explainerOptional.isPresent()){
            return ResponseEntity.ok(explainerOptional.get());
        }
        throw new ExplainerAlreadyExistsExcpetion(explainer.getName());
    }


    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such explainer")
    private static class NoExplainerException extends RuntimeException {

        public NoExplainerException(Long id) {
            super("No such explainer with id: "+id);
        }
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Explainer already exists")
    private static class ExplainerAlreadyExistsExcpetion extends RuntimeException {
        public ExplainerAlreadyExistsExcpetion(String name) {
            super("A explainer with name: "+name+" already exists");
        }
    }

}

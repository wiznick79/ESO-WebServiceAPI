package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.services.DegreeService;
import edu.ufp.nk.ws1.services.ExplainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/explainer")
public class ExplainerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExplainerService explainerService;
    private DegreeService degreeService;

    //Constructor
    @Autowired
    public ExplainerController(ExplainerService explainerService, DegreeService degreeService) {
        this.explainerService = explainerService;
        this.degreeService = degreeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Explainer>> searchExplainers(@RequestParam Map<String, String> query) {
        System.out.println(query);
        return ResponseEntity.ok(this.explainerService.filterExplainers(query));
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET)
    public ResponseEntity<Explainer> getExplainerById(@PathVariable("id") long id) {
        this.logger.info("Received a get request");

        Optional<Explainer> optionalExplainer = this.explainerService.findById(id);
        if (optionalExplainer.isPresent()) {
            return ResponseEntity.ok(optionalExplainer.get());
        }
        throw new NoExplainerException();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Explainer> getExplainerByName(@PathVariable("name") String name) {
        this.logger.info("Received a get request");

        Optional<Explainer> optionalExplainer = this.explainerService.findByName(name);
        if (optionalExplainer.isPresent()) {
            return ResponseEntity.ok(optionalExplainer.get());
        }
        throw new NoExplainerException();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explainer> createExplainer(@RequestBody Explainer explainer) {
        this.logger.info("Received a post request");

        Optional<Explainer> explainerOptional = this.explainerService.createExplainer(explainer);
        if (explainerOptional.isPresent()) {
            return ResponseEntity.ok(explainerOptional.get());
        }
        throw new ExplainerAlreadyExistsExcpetion(explainer.getName());
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explainer> createAvailability(@RequestBody Availability availability) {
        this.logger.info("Received a put request");

        if (this.explainerService.findByName(availability.getExplainer().getName()).isEmpty())
            throw new NoExplainerException();

        Optional<Explainer> explainerOptional = this.explainerService.createAvailability(availability);
        if (explainerOptional.isPresent()) {
            return ResponseEntity.ok(explainerOptional.get());
        }
        throw new BadAvailabilityException();
    }

    @PutMapping(value = "/{degree}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explainer> addDegree(@PathVariable("degree") String degreeName, @RequestBody Explainer explainer) {
        this.logger.info("Received a put request");

        if (this.explainerService.findByName(explainer.getName()).isEmpty())
            throw new NoExplainerException();
        if (this.degreeService.findByName(degreeName).isEmpty())
            throw new NoDegreeException();

        Optional<Explainer> explainerOptional = this.explainerService.addDegree(explainer, degreeName);

        if (explainerOptional.isPresent()) {
            return ResponseEntity.ok(explainerOptional.get());
        }

        throw new NoExplainerException();
    }

    @PostMapping(value = "/{language}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explainer> addLanguage(@PathVariable("language") String language, @RequestBody Explainer explainer) {
        this.logger.info("Received a post request");

        if (this.explainerService.findByName(explainer.getName()).isEmpty())
            throw new NoExplainerException();

        Optional<Explainer> explainerOptional = this.explainerService.addLanguage(explainer, language);

        if (explainerOptional.isPresent()) {
            return ResponseEntity.ok(explainerOptional.get());
        }

        throw new NoExplainerException();
    }



    /*
     * EXCEPTIONS
     */

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such explainer")
    private static class NoExplainerException extends RuntimeException {
        public NoExplainerException() {
            super("No such explainer");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Explainer already exists")
    private static class ExplainerAlreadyExistsExcpetion extends RuntimeException {
        public ExplainerAlreadyExistsExcpetion(String name) {
            super("An explainer with name: " + name + " already exists");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad availability")
    private static class BadAvailabilityException extends RuntimeException {
        public BadAvailabilityException() {
            super("Can't create this availability");
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such degree")
    private static class NoDegreeException extends RuntimeException {
        public NoDegreeException() {
            super("No such degree");
        }
    }
}

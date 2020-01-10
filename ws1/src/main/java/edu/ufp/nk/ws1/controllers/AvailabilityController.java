package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.services.AvailabilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Controller
@RequestMapping ("/availability")
public class AvailabilityController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private AvailabilityService availabilityService;

    //Constructor
    @Autowired
    public AvailabilityController(AvailabilityService availabilityService) {
        
        this.availabilityService = availabilityService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Availability>> getAllAvailabilities() {
        this.logger.info("Received a get request");
        
        return ResponseEntity.ok(this.availabilityService.findAll());
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET)
    public ResponseEntity<Availability> getAvailability(@PathVariable("id") long id) throws NoAvailabilityException {
        this.logger.info("Received a get request");
        Optional<Availability> optionalAvailability = this.availabilityService.findById(id);
        if (optionalAvailability.isPresent()) {
            return ResponseEntity.ok(optionalAvailability.get());
        }
        throw new AvailabilityController.NoAvailabilityException(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    // TODO: Use code 201: Created
    // TODO: Change return code when already exists.
    // TODO: Verificar como no Availability
    public ResponseEntity<Availability> createAvailability(@RequestBody Availability availability) {
        Optional<Availability> availabilityOptional = this.availabilityService.createAvailability(availability);
        if (availabilityOptional.isPresent()) {
            return ResponseEntity.ok(availabilityOptional.get());
        }
        throw new AvailabilityController.AvailabilityAlreadyExistsException(availability.getStart(),availability.getDayOfWeek());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such availability")
    private static class NoAvailabilityException extends RuntimeException {
        public NoAvailabilityException(Long id) {
            super("No such availability with id: " + id);
        }
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Availability already exists")
    private static class AvailabilityAlreadyExistsException extends RuntimeException {
        public AvailabilityAlreadyExistsException(LocalTime time, int day) {
            super("An availability on day: "+day+" "+time+" already exists");
        }
    }
}

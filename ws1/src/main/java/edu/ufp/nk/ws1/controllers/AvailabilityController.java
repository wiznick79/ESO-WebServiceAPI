package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.repositories.AvailabilityRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping ("/availability")
public class AvailabilityController {
    private AvailabilityRepo availabilityRepo;

    //Constructor
    public AvailabilityController(AvailabilityRepo availabilityRepo) {
        this.availabilityRepo = availabilityRepo;
    }

    @GetMapping
    public ResponseEntity<Iterable<Availability>> getAllAvailabilities(){
        return ResponseEntity.ok(this.availabilityRepo.findAll());
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Availability> searchAvailability(@PathVariable long id){
        Availability found = availabilityRepo.findById(id);

        if(found == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(found);
    }

    @PostMapping
    // TODO: Use code 201: Created
    // TODO: Change return code when already exists.
    // TODO: Verificar como no Appointment
    public ResponseEntity<Availability> addAvailability(@Valid @RequestBody Availability novo){
        if(availabilityRepo.findByDay_of_weekAndStart(novo.getDay_of_week(),novo.getStart()) != null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(availabilityRepo.save(novo));
    }
}

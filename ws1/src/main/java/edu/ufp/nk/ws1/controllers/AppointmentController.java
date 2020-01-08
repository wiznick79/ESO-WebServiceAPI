package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Appointment;
import edu.ufp.nk.ws1.repositories.AppointmentRepo;
import edu.ufp.nk.ws1.services.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    private AppointmentRepo appointmentRepo;
    private AppointmentService appointmentService;

    //Constructor
    public AppointmentController(AppointmentRepo appointmentRepo){this.appointmentRepo=appointmentRepo;}

    @GetMapping
    public ResponseEntity<Iterable<Appointment>> getAllAppointments(){
        return ResponseEntity.ok(this.appointmentRepo.findAll());
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Appointment> searchAppointment(@PathVariable long id){
        Appointment found = appointmentRepo.findById(id);

        if(found == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(found);
    }

    @PostMapping
    // TODO: Use code 201: Created
    // TODO: Change return code when already exists.
    // TODO: Verificar o findByStartAndDate
    public ResponseEntity<Appointment> addAppointment(@Valid @RequestBody Appointment novo){
        if (appointmentRepo.findByStartAndDate(novo.getStart(), novo.getDate())!=null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointmentRepo.save(novo));
    }



}

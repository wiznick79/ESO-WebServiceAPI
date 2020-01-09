package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Appointment;
import edu.ufp.nk.ws1.services.AppointmentService;
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
@RequestMapping("/appointment")
public class AppointmentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AppointmentService appointmentService;

    //Constructor
    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService=appointmentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Appointment>> getAllAppointments() {
        this.logger.info("Received a get request");

        return ResponseEntity.ok(this.appointmentService.findAll());
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET)
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") long id) throws NoAppointmentException {
        this.logger.info("Received a get request");

        Optional<Appointment> optionalAppointment = this.appointmentService.findById(id);
        if (optionalAppointment.isPresent()){
            return ResponseEntity.ok(optionalAppointment.get());
        }
        throw new NoAppointmentException(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    // TODO: Use code 201: Created
    // TODO: Change return code when already exists.
    // TODO: Verificar o findByStartAndDate
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment){
        Optional<Appointment> appointmentOptional = this.appointmentService.createAppointment(appointment);
        if (appointmentOptional.isPresent()) {
            return ResponseEntity.ok(appointmentOptional.get());
        }
        throw new AppointmentController.AppointmentAlreadyExistsException(appointment.getStart(),appointment.getDate());
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such appointment")
    private static class NoAppointmentException extends RuntimeException {
        public NoAppointmentException(Long id) {
            super("No such appointment with id: " + id);
        }
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Appointment already exists")
    private static class AppointmentAlreadyExistsException extends RuntimeException {

        public AppointmentAlreadyExistsException(LocalTime time, LocalDate date) {
            super("An appointment on date: "+date+" "+time+" already exists");
        }
    }

}

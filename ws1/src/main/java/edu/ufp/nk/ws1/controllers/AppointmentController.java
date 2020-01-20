package edu.ufp.nk.ws1.controllers;

import edu.ufp.nk.ws1.models.Appointment;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.models.Student;
import edu.ufp.nk.ws1.services.AppointmentService;
import edu.ufp.nk.ws1.services.ExplainerService;
import edu.ufp.nk.ws1.services.StudentService;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AppointmentService appointmentService;
    private ExplainerService explainerService;
    private StudentService studentService;

    //Constructor
    @Autowired
    public AppointmentController(AppointmentService appointmentService, ExplainerService explainerService, StudentService studentService) {
        this.appointmentService = appointmentService;
        this.explainerService = explainerService;
        this.studentService = studentService;
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
        if (optionalAppointment.isPresent()) {
            return ResponseEntity.ok(optionalAppointment.get());
        }
        throw new NoAppointmentException();
    }

    @RequestMapping(value = "/date={date}", method = RequestMethod.GET)
    public ResponseEntity<Appointment> getAppointmentByDate(@PathVariable("date") String date) throws NoAppointmentException{
        this.logger.info("Received a get request");
        LocalDate localDate = LocalDate.parse(date);
        Optional<Appointment> optionalAppointment = this.appointmentService.findByDate(localDate);
        if (optionalAppointment.isPresent()) {
            return ResponseEntity.ok(optionalAppointment.get());
        }
        throw new NoAppointmentException();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        this.logger.info("Received a post request");

        Optional<Student> optionalStudent = this.studentService.findByName(appointment.getStudent().getName());
        if (optionalStudent.isEmpty())
            throw new NoSuchStudentException(appointment.getStudent().getName());
        Optional<Explainer> optionalExplainer = this.explainerService.findByName(appointment.getExplainer().getName());
        if (optionalExplainer.isEmpty())
            throw new NoSuchExplainerException(appointment.getExplainer().getName());

        Optional<Appointment> appointmentOptional = this.appointmentService.createAppointment(appointment);
        if (appointmentOptional.isPresent()) {
            return ResponseEntity.ok(appointmentOptional.get());
        }
        throw new AppointmentController.AppointmentAlreadyExistsException(appointment.getStart(), appointment.getDate());
    }

    /*
     * EXCEPTIONS
     */

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such appointment")
    private static class NoAppointmentException extends RuntimeException {
        public NoAppointmentException() {
            super("No such appointment");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Appointment already exists")
    private static class AppointmentAlreadyExistsException extends RuntimeException {
        public AppointmentAlreadyExistsException(LocalTime time, LocalDate date) {
            super("An appointment on date: " + date + " " + time + " already exists");
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such student")
    private static class NoSuchStudentException extends RuntimeException {
        public NoSuchStudentException(String name) {
            super("No such student with name: " + name);
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such explainer")
    private static class NoSuchExplainerException extends RuntimeException {
        public NoSuchExplainerException(String name) {
            super("No such explainer with name: " + name);
        }
    }
}

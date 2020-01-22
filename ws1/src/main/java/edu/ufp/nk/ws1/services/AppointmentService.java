package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Appointment;
import edu.ufp.nk.ws1.models.Availability;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.models.Student;
import edu.ufp.nk.ws1.repositories.AppointmentRepo;
import edu.ufp.nk.ws1.repositories.ExplainerRepo;
import edu.ufp.nk.ws1.repositories.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class AppointmentService {
    private AppointmentRepo appointmentRepo;
    private ExplainerRepo explainerRepo;
    private StudentRepo studentRepo;
    // private FilterAppointmentService filterAppointmentService;

    @Autowired
    public AppointmentService(AppointmentRepo appointmentRepo, ExplainerRepo explainerRepo, StudentRepo studentRepo) {
        this.appointmentRepo = appointmentRepo;
        this.explainerRepo = explainerRepo;
        this.studentRepo = studentRepo;
        // this.filterAppointmentService = filterAppointmentService;
    }

    public Set<Appointment> findAll() {
        Set<Appointment> appointments = new HashSet<>();
        for (Appointment appointment : this.appointmentRepo.findAll()) {
            appointments.add(appointment);
        }
        return appointments;
    }

    public Optional<Appointment> findById(Long id) {
        return this.appointmentRepo.findById(id);
    }

    public Optional<Appointment> createAppointment(Appointment appointment) {
        Optional<Explainer> optionalExplainer = this.explainerRepo.findByName(appointment.getExplainer().getName());
        Optional<Student> optionalStudent = this.studentRepo.findByName((appointment.getStudent().getName()));

        if (optionalExplainer.isEmpty() || optionalStudent.isEmpty()) {
            return Optional.empty();
        }

        Optional<Appointment> optionalAppointment = this.appointmentRepo.findByDateAndExplainerAndStudentAndStart(appointment.getDate(), optionalExplainer.get(), optionalStudent.get(), appointment.getStart());
        Optional<Appointment> optionalAppointment2 = this.appointmentRepo.findByDateAndExplainerAndStart(appointment.getDate(), optionalExplainer.get(), appointment.getStart());
        if(optionalAppointment.isPresent() || optionalAppointment2.isPresent()) {
            return Optional.empty();
        }

        Set<Availability> availabilities = optionalExplainer.get().getAvailabilities();
        for (Availability e : availabilities) {

            // Has availabilitie
            if(appointment.getDate().isEqual(e.getDay())
                && (appointment.getStart().isAfter(e.getStart()) || appointment.getStart().equals(e.getStart()))
                    && (appointment.getStart().isBefore(e.getEnd().minusHours(1)) || appointment.getStart().equals(e.getEnd().minusHours(1))))  {

                // Its not Free
                for (Appointment a: this.appointmentRepo.findAllByExplainer(optionalExplainer.get())){
                    if(a.getDate().isEqual(appointment.getDate())
                        && (appointment.getStart().isAfter( a.getStart()) ||  appointment.getStart() == a.getStart())
                            && appointment.getStart().isBefore(a.getStart().plusHours(1))) {
                                return Optional.empty();
                        }
                }

                appointment.setExplainer(optionalExplainer.get());
                appointment.setStudent(optionalStudent.get());
                this.appointmentRepo.save(appointment);
                return Optional.of(appointment);
            }
        }


        return Optional.empty();
    }

    /*
    public Set<Appointment> filterAppointments (Map<String, String> searchParams){
        FilterAppointmentObject filterAppointmentObject = new FilterAppointmentObject(searchParams);
        Set<Appointment> appointments = this.findAll();

        return this.filterAppointmentService.filter(appointments, filterAppointmentObject);
    }
    */

    public Optional<Appointment> findByDate(LocalDate date) {
        return this.appointmentRepo.findByDate(date);
    }

    public Optional<Appointment> findByStartAndDate(LocalTime time, LocalDate date) {
        return this.appointmentRepo.findByStartAndDate(time, date);
    }

}

package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Appointment;
import edu.ufp.nk.ws1.repositories.AppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppointmentService {
    private AppointmentRepo appointmentRepo;
   // private FilterAppointmentService filterAppointmentService;

    @Autowired
    public AppointmentService(AppointmentRepo appointmentRepo){
        this.appointmentRepo = appointmentRepo;
       // this.filterAppointmentService = filterAppointmentService;
    }

    public Set<Appointment> findAll(){
        Set<Appointment> appointments = new HashSet<>();
        for (Appointment appointment:this.appointmentRepo.findAll()){
            appointments.add(appointment);
        }
        return appointments;
    }

    public Optional<Appointment> findById(Long id) {
        return this.appointmentRepo.findById(id);
    }

    public Optional<Appointment> createAppointment(Appointment appointment){
        Optional<Appointment> optionalAppointment = this.appointmentRepo.findByStartAndDate(appointment.getStart(),appointment.getDate());
        if (optionalAppointment.isPresent()){
            return Optional.empty();
        }
        Appointment createAppointment= this.appointmentRepo.save(appointment);
        return Optional.of(createAppointment);
    }
/*
    public Set<Appointment> filterAppointments (Map<String, String> searchParams){
        FilterAppointmentObject filterAppointmentObject = new FilterAppointmentObject(searchParams);
        Set<Appointment> appointments = this.findAll();

        return this.filterAppointmentService.filter(appointments, filterAppointmentObject);
    }
 */

    public Optional<Appointment> findByDate (LocalDate date) {
        return this.appointmentRepo.findByDate(date);
    }

    public Optional<Appointment> findByStartAndDate (LocalTime time, LocalDate date) {
        return this.appointmentRepo.findByStartAndDate(time, date);
    }

}

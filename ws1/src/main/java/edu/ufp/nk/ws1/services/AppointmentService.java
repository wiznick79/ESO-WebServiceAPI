package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Appointment;
import edu.ufp.nk.ws1.models.Explainer;
import edu.ufp.nk.ws1.models.Student;
import edu.ufp.nk.ws1.repositories.AppointmentRepo;
import edu.ufp.nk.ws1.repositories.ExplainerRepo;
import edu.ufp.nk.ws1.repositories.StudentRepo;
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
    private ExplainerRepo explainerRepo;
    private StudentRepo studentRepo;
   // private FilterAppointmentService filterAppointmentService;

    @Autowired
    public AppointmentService(AppointmentRepo appointmentRepo, ExplainerRepo explainerRepo, StudentRepo studentRepo){
        this.appointmentRepo = appointmentRepo;
        this.explainerRepo = explainerRepo;
        this.studentRepo = studentRepo;
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
        Optional<Explainer> optionalExplainer = this.explainerRepo.findByName(appointment.getExplainer().getName());
        Optional<Student> optionalStudent = this.studentRepo.findByName((appointment.getStudent().getName()));

        if (optionalAppointment.isPresent() || optionalExplainer.isEmpty() || optionalStudent.isEmpty()){
            return Optional.empty();
        }

        appointment.setExplainer(optionalExplainer.get());
        appointment.setStudent(optionalStudent.get());
        this.appointmentRepo.save(appointment);

        return Optional.of(appointment);
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

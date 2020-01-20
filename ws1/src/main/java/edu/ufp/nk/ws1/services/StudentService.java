package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Student;
import edu.ufp.nk.ws1.repositories.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    private StudentRepo studentRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public Set<Student> findAll() {
        Set<Student> students = new HashSet<>();
        for (Student student : this.studentRepo.findAll()) {
            students.add(student);
        }
        return students;
    }

    public Optional<Student> findById(Long id) {
        return this.studentRepo.findById(id);
    }

    public Optional<Student> findByName(String name) {
        return this.studentRepo.findByName(name);
    }

    public Optional<Student> findByNumber(int number) {
        return this.studentRepo.findByStudentNumber(number);
    }

    public Optional<Student> createStudent(Student student) {
        Optional<Student> optionalStudent = this.studentRepo.findByStudentNumber(student.getStudentNumber());
        if (optionalStudent.isPresent()) {
            return Optional.empty();
        }
        Student createdStudent = this.studentRepo.save(student);
        return Optional.of(createdStudent);
    }

}

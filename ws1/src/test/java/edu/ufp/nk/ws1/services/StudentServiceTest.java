package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Student;
import edu.ufp.nk.ws1.repositories.StudentRepo;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = StudentService.class)
@VisibleForTesting
class StudentServiceTest {
    @Autowired
    private StudentService studentService;
    @MockBean
    private StudentRepo studentRepo;

    @Test
    @VisibleForTesting
    void accessStudent(){
        Student student = new Student("Alvaro Magalhaes", 37000);
        Student student1 = new Student("Nikos Perris", 37001);
        Student student2 = new Student("Pedro Alves", 37002);
        this.studentRepo.save(student);
        this.studentRepo.save(student1);
        this.studentRepo.save(student2);
        Set<Student> students = new HashSet<>();
        students.add(student2);
        students.add(student);
        students.add(student1);
        when(this.studentService.findByNumber(37000)).thenReturn(Optional.of(student));
        assertNotNull(student);
        when(this.studentService.findByName(student.getName())).thenReturn(Optional.of(student));
        when(this.studentService.findAll()).thenReturn(students);
        when(this.studentService.createStudent(student)).thenReturn(Optional.of(student));
        when(this.studentService.findById(student.getId())).thenReturn(Optional.of(student));
    }
}

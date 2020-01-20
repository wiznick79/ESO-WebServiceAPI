package edu.ufp.nk.ws1;

import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.models.Course;
import edu.ufp.nk.ws1.models.College;
import edu.ufp.nk.ws1.models.Student;
import edu.ufp.nk.ws1.repositories.DegreeRepo;
import edu.ufp.nk.ws1.repositories.StudentRepo;
import edu.ufp.nk.ws1.repositories.CourseRepo;
import edu.ufp.nk.ws1.repositories.CollegeRepo;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private DegreeRepo degreeRepo;
    private CourseRepo courseRepo;
    private StudentRepo studentRepo;
    private CollegeRepo collegeRepo;


    public Bootstrap(DegreeRepo degreeRepo, CourseRepo courseRepo, StudentRepo studentRepo, CollegeRepo collegeRepo) {
        this.setDegreeRepo(degreeRepo);
        this.setCourseRepo(courseRepo);
        this.setStudentRepo(studentRepo);
        this.setCollegeRepo(collegeRepo);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadData();

    }

    // Testing data
    public void loadData() {
        Degree enfermagem = new Degree("Enfermagem");
        Degree engCivil = new Degree("Engenharia Civil");
        Degree engInfo = new Degree("Engenharia Informática");
        Degree psicologia = new Degree("Psicologia");

        Course c1 = new Course("Laboratorio de Programação");
        Course c2 = new Course("Redes de Computadores I");
        Course c3 = new Course("Base de Dados I");
        Course c4 = new Course("Multimédia I");

        Student s1 = new Student("Alvaro Magalhães", 37000);
        Student s2 = new Student("Pedro Alves", 123124);

        College cl1 = new College("Faculdade Ciencias");
        College cl2 = new College("Faculdade de saude");


        enfermagem.setCollege(cl2);
        engCivil.setCollege(cl1);
        engInfo.setCollege(cl1);

        c1.setDegree(engInfo);
        c2.setDegree(engCivil);
        c3.setDegree(psicologia);
        c4.setDegree(enfermagem);

        this.getDegreeRepo().save(enfermagem);
        this.getDegreeRepo().save(engCivil);
        this.getDegreeRepo().save(engInfo);
        this.getDegreeRepo().save(psicologia);

        this.getCourseRepo().save(c1);
        this.getCourseRepo().save(c2);
        this.getCourseRepo().save(c3);
        this.getCourseRepo().save(c4);

        this.getStudentRepo().save(s1);
        this.getStudentRepo().save(s2);

        this.getCollegeRepo().save(cl1);
        this.getCollegeRepo().save(cl2);

    }


    // Gets & Sets
    public DegreeRepo getDegreeRepo() {
        return this.degreeRepo;
    }

    public void setDegreeRepo(DegreeRepo degreeRepo) {
        this.degreeRepo = degreeRepo;
    }

    public CourseRepo getCourseRepo() {
        return this.courseRepo;
    }

    public void setCourseRepo(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    public StudentRepo getStudentRepo() {
        return this.studentRepo;
    }

    public void setStudentRepo(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public CollegeRepo getCollegeRepo() {
        return collegeRepo;
    }

    public void setCollegeRepo(CollegeRepo collegeRepo) {
        this.collegeRepo = collegeRepo;
    }
}

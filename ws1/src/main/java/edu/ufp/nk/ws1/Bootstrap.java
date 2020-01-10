package edu.ufp.nk.ws1;

import edu.ufp.nk.ws1.models.*;
import edu.ufp.nk.ws1.repositories.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private DegreeRepo degreeRepo;
	private CourseRepo courseRepo;


	public Bootstrap(DegreeRepo degreeRepo, CourseRepo courseRepo) {
		this.setDegreeRepo(degreeRepo);
		this.setCourseRepo(courseRepo);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		loadData();

	}

	// Testing data
	public void loadData(){
		Degree enfermagem = new Degree("Enfermagem");
		Degree engCivil = new Degree("Engenharia Civil");
		Degree engInfo = new Degree("Engenharia Informática");
		Degree psicologia = new Degree("Psicologia");

		Course c1 = new Course("Laboratorio de Programação");
		Course c2 = new Course("Redes de Computadores I");
		Course c3 = new Course("Base de Dados I");
		Course c4 = new Course("Multimédia I");

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

		System.out.println();
		System.out.println(this.getDegreeRepo().findAll());
		System.out.println();
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

}

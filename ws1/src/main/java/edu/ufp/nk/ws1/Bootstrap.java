package edu.ufp.nk.ws1;

import edu.ufp.nk.ws1.models.*;
import edu.ufp.nk.ws1.repositories.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Transactional
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private DegreeRepo degreeRepo;



	public Bootstrap(DegreeRepo degreeRepo) {
		this.setDegreeRepo(degreeRepo);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		Degree engInfo = new Degree("Engenharia Informática");
		Degree engCivil = new Degree("Engenharia Civil");
		Degree psicologia = new Degree("Psicologia");
		Degree enfermagem = new Degree("Enfermagem");

		this.getDegreeRepo().save(engInfo);
		this.getDegreeRepo().save(engCivil);
		this.getDegreeRepo().save(psicologia);
		this.getDegreeRepo().save(enfermagem);

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
}

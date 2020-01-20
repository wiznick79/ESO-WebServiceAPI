package edu.ufp.nk.ws2;

import edu.ufp.nk.ws2.models.University;
import edu.ufp.nk.ws2.repositories.UniversityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UniversityRepo universityRepo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadData();
    }

    public void loadData() {
        University university = new University("Universidade Fernando Pessoa", "http://localhost:8081/");
        University university2 = new University("ESEP", "http://localhost:8082/");
        University university3 = new University("ISEP", "http://localhost:8083/");

        universityRepo.save(university);
        universityRepo.save(university2);
        universityRepo.save(university3);

    }
}

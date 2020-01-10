package edu.ufp.nk.ws2.services;

import edu.ufp.nk.ws2.models.University;
import edu.ufp.nk.ws2.repositories.UniversityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UniversityService {

    private UniversityRepo universityRepo;

    @Autowired
    public UniversityService(UniversityRepo universityRepo){
        this.universityRepo =  universityRepo;
    }

    public Set<University> findAll() {
        Set<University> universities=new HashSet<>();
        for(University university:this.universityRepo.findAll()){
            universities.add(university);
        }
        return universities;
    }

    public Optional<University> findByName(String name) {

        return this.universityRepo.findByName(name);
    }

    public Optional<University> findById(Long id) {

        return this.universityRepo.findById(id);
    }

    public Optional<University> createUniversity (University university) {
        Optional<University> optionalUniversity = this.universityRepo.findByName(university.getName());
        if(optionalUniversity.isPresent()){
            return Optional.empty();
        }
        University createdUniversity=this.universityRepo.save(university);
        return Optional.of(createdUniversity);
    }
}

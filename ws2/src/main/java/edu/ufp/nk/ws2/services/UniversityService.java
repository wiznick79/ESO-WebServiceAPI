package edu.ufp.nk.ws2.services;

import edu.ufp.nk.ws2.models.University;
import edu.ufp.nk.ws2.repositories.UniversityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UniversityService {

    @Autowired
    private UniversityRepo universityRepo;

    public Iterable<University> getAllUniversities(){
        return universityRepo.findAll();
    }

    public Optional<University> getUniversityById(Long id){
        if (universityRepo.existsById(id)){
            return universityRepo.findById(id);
        }
        return Optional.empty();
    }

    public Optional<University> createUniversity(University university){
        Optional<University> optionalUniversity = this.universityRepo.findByName(university.getName());
        if (optionalUniversity.isPresent()){
            return Optional.empty();
        }
        University createUniversity = this.universityRepo.save(university);
        return Optional.of(createUniversity);
    }
}

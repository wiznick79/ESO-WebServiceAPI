package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.College;
import edu.ufp.nk.ws1.repositories.CollegeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CollegeService {

    private CollegeRepo collegeRepo;

    @Autowired
    public CollegeService(CollegeRepo collegeRepo){

        this.collegeRepo = collegeRepo;
    }

    public Set<College> findAll() {
        Set<College> colleges=new HashSet<>();
        for(College college:this.collegeRepo.findAll()){
            colleges.add(college);
        }
        return colleges;
    }

    public Optional<College> findByName(String name) {

        return this.collegeRepo.findByName(name);
    }

    public Optional<College> findById(Long id) {

        return this.collegeRepo.findById(id);
    }

    public Optional<College> createCollege (College college) {
        Optional<College> optionalCollege = this.collegeRepo.findByName(college.getName());
        if(optionalCollege.isPresent()){
            return Optional.empty();
        }
        College createdCollege=this.collegeRepo.save(college);
        return Optional.of(createdCollege);
    }

}

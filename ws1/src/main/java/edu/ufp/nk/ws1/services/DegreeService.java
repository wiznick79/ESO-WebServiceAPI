package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.College;
import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.repositories.CollegeRepo;
import edu.ufp.nk.ws1.repositories.DegreeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class DegreeService {
    private DegreeRepo degreeRepo;
    private CollegeRepo collegeRepo;

    @Autowired
    public DegreeService(DegreeRepo degreeRepo, CollegeRepo collegeRepo) {
        this.degreeRepo = degreeRepo;
        this.collegeRepo = collegeRepo;
    }

    public Set<Degree> findAll() {
        Set<Degree> degrees = new HashSet<>();
        for (Degree degree : this.degreeRepo.findAll()) {
            degrees.add(degree);
        }
        return degrees;
    }

    public Optional<Degree> findById(Long id) {

        return this.degreeRepo.findById(id);
    }

    public Optional<Degree> createDegreeByCollege(Degree degree, Long college) {
        Optional<Degree> optionalDegree = this.degreeRepo.findByName(degree.getName());
        Optional<College> optionalCollege = this.collegeRepo.findById(college);

        if (optionalDegree.isPresent()) {
            return Optional.empty();
        }

        if (optionalCollege.isPresent()) {
            degree.setCollege(optionalCollege.get());
        } else return Optional.empty();

        Degree createDegree = this.degreeRepo.save(degree);
        return Optional.of(createDegree);
    }

    public Optional<Degree> findByName(String name) {
        return this.degreeRepo.findByName(name);
    }
}

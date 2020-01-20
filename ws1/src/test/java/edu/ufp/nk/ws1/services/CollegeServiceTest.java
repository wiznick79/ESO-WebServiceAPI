package edu.ufp.nk.ws1.services;


import edu.ufp.nk.ws1.models.College;
import edu.ufp.nk.ws1.repositories.CollegeRepo;
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

@SpringBootTest(classes = CollegeService.class)
@VisibleForTesting
class CollegeServiceTest{
    @Autowired
    private CollegeService collegeService;
    @MockBean
    private CollegeRepo collegeRepo;

    @Test
    @VisibleForTesting
    void accessCollege(){
        College college = new College("Ciencias");
        College college2 = new College("Saude");

        this.collegeRepo.save(college);
        college.setId(1L);

        Set<College> colleges = new HashSet<>();
        colleges.add(college);
        colleges.add(college2);

        when(this.collegeService.findById(1L)).thenReturn(Optional.of(college));
        assertNotNull(college);

        when(this.collegeService.findAll()).thenReturn(colleges);

        when(this.collegeService.findByName("Ciencias")).thenReturn(Optional.of(college));

        this.collegeService.createCollege(college);


    }
}
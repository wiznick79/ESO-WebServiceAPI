package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.College;
import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.repositories.CollegeRepo;
import edu.ufp.nk.ws1.repositories.DegreeRepo;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = DegreeService.class)
@VisibleForTesting
class DegreeServiceTest {
    @Autowired
    private DegreeService degreeService;
    @MockBean
    private DegreeRepo degreeRepo;
    @MockBean
    private CollegeRepo collegeRepo;

    @Test
    @VisibleForTesting
    void accessDegree() {
        Degree degree = new Degree("Engenharia Informatica");
        Degree degree1 = new Degree("Psicologia");
        this.degreeRepo.save(degree);
        this.degreeRepo.save(degree1);
        Set<Degree> degrees = new HashSet<>();
        degrees.add(degree1);
        degrees.add(degree);
        College college = new College("Ciencias");
        collegeRepo.save(college);
        when(this.degreeService.findById(1L)).thenReturn(Optional.of(degree));
        assertNotNull(degree);
        when(this.degreeService.findAll()).thenReturn(degrees);
        when(this.degreeService.findByName(degree.getName())).thenReturn(Optional.of(degree));
        this.degreeService.createDegreeByCollege(degree,college.getId());

    }

}


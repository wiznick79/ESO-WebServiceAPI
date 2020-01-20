package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Course;
import edu.ufp.nk.ws1.models.Degree;
import edu.ufp.nk.ws1.repositories.CourseRepo;
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

@SpringBootTest(classes = CourseService.class)
@VisibleForTesting
class CourseServiceTest {
    @Autowired
    private CourseService courseService;
    @MockBean
    private CourseRepo courseRepo;
    @MockBean
    private DegreeRepo degreeRepo;

    @Test
    @VisibleForTesting
    void accessCourse(){
        Course course = new Course("Math");
        Course course1 = new Course("English");
        this.courseRepo.save(course);
        this.courseRepo.save(course1);
        Set<Course> courses =new HashSet<>();
        courses.add(course1);
        courses.add(course);
        Degree degree = new Degree("Engenharia Informatica");
        this.degreeRepo.save(degree);
        course.setDegree(degree);
        course1.setDegree(degree);

        when(this.courseService.findById(1L)).thenReturn(Optional.of(course));
        assertNotNull(course);

        when(this.courseService.findAll()).thenReturn(courses);

        when(this.courseService.findByName("Math")).thenReturn(Optional.of(course));

        when(this.courseService.createCourseByDegree(course,degree.getId())).thenReturn(Optional.of(course));
    }


}

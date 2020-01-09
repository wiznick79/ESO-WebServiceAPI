package edu.ufp.nk.ws1.services;

import edu.ufp.nk.ws1.models.Course;
import edu.ufp.nk.ws1.repositories.CourseRepo;
import edu.ufp.nk.ws1.services.filters.course.FilterCourseObject;
import edu.ufp.nk.ws1.services.filters.course.FilterCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {
    private CourseRepo courseRepo;
    private FilterCourseService filterCourseService;

    @Autowired
    public CourseService(CourseRepo courseRepo, FilterCourseService filterCourseService){
        this.courseRepo=courseRepo;
        this.filterCourseService = filterCourseService;
    }

    public Set<Course> findAll(){
        Set<Course> courses = new HashSet<>();
        for (Course course:this.courseRepo.findAll()){
            courses.add(course);
        }
        return courses;
    }


    public Optional<Course> findById(Long id) {return this.courseRepo.findById(id);}

    public Optional<Course> createCourse(Course course){
        Optional<Course> optionalCourse = this.courseRepo.findByName(course.getName());
        if (optionalCourse.isPresent()){
            return Optional.empty();
        }
        Course createCourse= this.courseRepo.save(course);
        return Optional.of(createCourse);
    }



    public Set<Course> filterCourses (Map<String, String> searchParams){
        FilterCourseObject filterCourseObject = new FilterCourseObject(searchParams);
        Set<Course> courses = this.findAll();

        return this.filterCourseService.filter(courses, filterCourseObject);
    }

    public Optional<Course> findByName (String name) { return this.courseRepo.findByName(name);}
}
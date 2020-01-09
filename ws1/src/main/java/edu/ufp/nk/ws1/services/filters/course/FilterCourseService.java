package edu.ufp.nk.ws1.services.filters.course;

import edu.ufp.nk.ws1.models.Course;
import edu.ufp.nk.ws1.services.filters.AndFilter;
import edu.ufp.nk.ws1.services.filters.FilterI;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FilterCourseService {

    public Set<Course> filter(Set<Course> courses, FilterCourseObject filterCourseObject){
        FilterI<Course> courseFilterByName = new CourseFilterByName(filterCourseObject.getName());

       /* FilterI<Course> courseName = new AndFilter<>(courseFilterByName)
        return new AndFilter<>(courseFilterByName)*/
        return null;
    }
}

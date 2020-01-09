package edu.ufp.nk.ws1.services.filters.course;


import edu.ufp.nk.ws1.models.Course;
import edu.ufp.nk.ws1.services.filters.FilterI;

import java.util.HashSet;
import java.util.Set;

public class CourseFilterByName implements FilterI<Course> {
    private String name;

    public CourseFilterByName(String name) {this.name =name;}

    @Override
    public Set<Course> filter(Set<Course> entities){
        if (name==null || name.isEmpty()){
            return entities;
        }
        Set<Course> courses = new HashSet<>();
        for (Course course:entities){
            if (course.getName()!=null && course.getName().equalsIgnoreCase(name)){
                courses.add(course);
            }
        }
        return courses;
    }
}

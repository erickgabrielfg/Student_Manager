package com.example.backend.service;

import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.utils.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository){
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Optional<Course> getById(Integer id){
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course){
        return courseRepository.save(course);
    }

    public Course updateCourse(Integer id, Course updateCourse){
        return courseRepository.findById(id).map(course ->{
            course.setName(updateCourse.getName());
            course.setAcronym(updateCourse.getAcronym());
            course.setShift(updateCourse.getShift());

            return courseRepository.save(course);
        }).orElseThrow(() -> new RuntimeException("Curso não encontrado"));
    }

    public void deleteCourse(Integer id){
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        List<User> users = userRepository.findByCourse(course);

        for (User user : users){
            user.setCourse(null);
        }

        courseRepository.delete(course);
    }
}

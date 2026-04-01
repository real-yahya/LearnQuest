package com.example.project_files.service.impl;

import com.example.project_files.entity.User;
import com.example.project_files.model.Course;
import com.example.project_files.model.userCourse;
import com.example.project_files.repository.CourseRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.project_files.repository.UserRepository;
import com.example.project_files.repository.UserCourseRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private CourseRepository c_repo;

    @PostConstruct// Will automatically be called when the web app runs
    public void initialiseCourse() {

        // Log check
        System.out.println("TEST");

        /* Use method to find number of records to check
           if courses have already been generated.*/
        if (c_repo.count() == 0) {
            Course course1 = new Course();
            course1.setName("Artificial Intelligence");
            course1.setCourse_id(1);
            c_repo.save(course1);

            Course course2 = new Course();
            course2.setName("Machine Learning");
            course2.setCourse_id(2);
            c_repo.save(course2);

            Course course3 = new Course();
            course3.setName("Data Structures");
            course3.setCourse_id(3);
            c_repo.save(course3);

            Course course4 = new Course();
            course4.setName("Software Engineering");
            course4.setCourse_id(4);
            c_repo.save(course4);

            Course course5 = new Course();
            course5.setName("Computer Networks");
            course5.setCourse_id(5);
            c_repo.save(course5);

            Course course6 = new Course();
            course6.setName("Web Development");
            course6.setCourse_id(6);
            c_repo.save(course6);

            Course course7 = new Course();
            course7.setName("Cyber Security");
            course7.setCourse_id(7);
            c_repo.save(course7);

            Course course8 = new Course();
            course8.setName("Database Management Systems");
            course8.setCourse_id(8);
            c_repo.save(course8);

            Course course9 = new Course();
            course9.setName("Operating Systems");
            course9.setCourse_id(9);
            c_repo.save(course9);

            Course course10 = new Course();
            course10.setName("Mobile App Development");
            course10.setCourse_id(10);
            c_repo.save(course10);
        }

    }
    public List<Course> getAllCourses() {
        Iterable<Course> coursesIterable = c_repo.findAll();
        List<Course> coursesList = new ArrayList<>();
        coursesIterable.forEach(coursesList::add); // Convert Iterable to List
        return coursesList;
    }

    public userCourse startCourse(Long userId, Long courseId, LocalDateTime startTime) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findById(Math.toIntExact(courseId)).orElseThrow(() -> new RuntimeException("Course not found"));

        // Assuming userCourseRepository handles userCourse entities
        // Check if there's an existing userCourse for this user and course
        Optional<userCourse> existingUserCourse = userCourseRepository.findByUser_IdAndCourse_Id(userId, Math.toIntExact(courseId));
        userCourse userCourseEntity;

        if(existingUserCourse.isPresent()) {
            // If there's an existing record, update the start time
            userCourseEntity = existingUserCourse.get();
        } else {
            // If not, create a new userCourse entity
            userCourseEntity = new userCourse();
            userCourseEntity.setUser(user);
            userCourseEntity.setCourse(course);
        }

        userCourseEntity.setTimeStart(startTime);
        // Additional logic here as necessary, e.g., setting default values or statuses

        return userCourseRepository.save(userCourseEntity);
    }
}

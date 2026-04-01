package com.example.project_files.controller;

import com.example.project_files.dto.UserDto;
import com.example.project_files.entity.CourseTracking;
import com.example.project_files.entity.User;
import com.example.project_files.model.Course;
import com.example.project_files.model.userCourse;
import com.example.project_files.repository.CourseRepository;
import com.example.project_files.repository.CourseTrackingRepository;
import com.example.project_files.repository.UserCourseRepository;
import com.example.project_files.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;


@Controller
public class PopularController {

    @Autowired
    UserServiceImpl userService;

    private final CourseTrackingRepository CourseTrackingRepository;
    private static final Logger logger = LoggerFactory.getLogger(PopularController.class);
    @Autowired
    public PopularController(CourseTrackingRepository courseTrackingRepository) {
        this.CourseTrackingRepository = courseTrackingRepository;
    }

    @GetMapping("/popularpage")
    public String showFavourites(Principal principal, Model model) {
        User currentUser = userService.fetchUser(principal);
        UserDto currentUserDTO = userService.mapToUserDto(currentUser);


        // Retrieve the list of favorite courses from the database
        Iterable<CourseTracking> favouritecourses = CourseTrackingRepository.findAll();

        // Add the courses to the model
        model.addAttribute("favouritecourses", favouritecourses);
        model.addAttribute("user", currentUserDTO);
        // Return the view name (popular.html)
        return "popular";
    }

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserCourseRepository userCourseRepository;

    @GetMapping("/course-direct/{course_id}")
    public String directCourse(
            @PathVariable("course_id") int course_id,
            Principal principal){

        // Get user and course
        User currentUser = userService.fetchUser(principal);
        Course course = courseRepository.findById(course_id).get();

        // Find userCourse with user and course
        userCourse currentu_course = userCourseRepository.findByUserAndCourse(currentUser, course);

        // Get ID of userCourse to pass to view course controller
        long id = currentu_course.getU_courseId();

        return "redirect:/viewCoursePage/"+id;
    }



}


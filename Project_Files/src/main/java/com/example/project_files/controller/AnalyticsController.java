package com.example.project_files.controller;

import com.example.project_files.model.Course;
import com.example.project_files.entity.User;
import com.example.project_files.model.userCourse;
import com.example.project_files.repository.CourseRepository;
import com.example.project_files.repository.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AnalyticsController {

    private final UserCourseRepository userCourseRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public AnalyticsController(UserCourseRepository userCourseRepository, CourseRepository courseRepository) {
        this.userCourseRepository = userCourseRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/testanalytics")
    public String testanalytics(Model model) {
        // Get list of all courses
        Iterable<Course> courses = courseRepository.findAll();


        // Create a map to store analytics data for each course
        Map<String, Map<String, Integer>> analyticsData = new HashMap<>();

        // Iterate through each course
        for (Course course : courses) {
            // Retrieve all userCourses related to the current course
            List<userCourse> userCourses = userCourseRepository.findByCourse(course);

            // Count the number of students who have started, completed, and rated the course
            int startedCount = 0;
            int completedCount = 0;
            int ratedCount = 0;

            for (userCourse uc : userCourses) {
                if (uc.getCourseStatus() != userCourse.CourseStatus.START) {
                    startedCount++;
                } if (uc.getCourseStatus() == userCourse.CourseStatus.COMPLETE) {
                    completedCount++;
                }
                if (uc.getRating() > 0) {
                    ratedCount++;
                }
            }

            // Store the analytics data for the current course in the map
            Map<String, Integer> courseAnalytics = new HashMap<>();
            courseAnalytics.put("startedCount", startedCount);
            courseAnalytics.put("completedCount", completedCount);
            courseAnalytics.put("ratedCount", ratedCount);

            // Store the analytics data for the current course in the main analytics data map
            analyticsData.put(course.getName(), courseAnalytics);
        }

        // Add the analytics data map to the model
        model.addAttribute("analyticsData", analyticsData);

        return "testanalytics";
    }
}



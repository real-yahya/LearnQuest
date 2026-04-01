package com.example.project_files.controller;


import com.example.project_files.dto.UserDto;
import com.example.project_files.entity.CourseTracking;
import com.example.project_files.entity.User;
import com.example.project_files.model.Course;
import com.example.project_files.model.userCourse;
import com.example.project_files.repository.CourseTrackingRepository;
import com.example.project_files.repository.UserCourseRepository;
import com.example.project_files.repository.CourseRepository;
import com.example.project_files.repository.UserRepository;
import com.example.project_files.service.impl.CourseService;
import com.example.project_files.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller


    public class CourseController {
    private UserCourseRepository repo;
    private UserRepository userepo;
    private CourseTrackingRepository courseTrackingRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    public CourseController(UserCourseRepository repo,UserRepository userepo, CourseTrackingRepository courseTrackingRepository) {
        this.repo = repo;
        this.userepo = userepo;
        this.courseTrackingRepository = courseTrackingRepository;

    }
    @GetMapping("courses/")
    public String getCourses(@RequestParam(name = "userID") long userID, Model model) {
        Optional<User> result = userepo.findById(userID);
        if (result.isPresent()) {
            List<userCourse> courses = repo.findByUser(result.get());
            model.addAttribute("courses", courses);
        }
        return "course_history";
    }



    @RequestMapping ("/updateCourse/{u_courseID}")
    // userCourse ID taken from form submission
    public String updateCourse(@PathVariable(name = "u_courseID") long u_courseID, Model model) {

        // Get userCourse ID from parameter
        // userCourseID is unique for all user and course combinations
        userCourse current_uCourse = repo.findById(u_courseID).get();

        //Switch case for courseStatus
        switch (current_uCourse.getCourseStatus()){
            case START -> {
                current_uCourse.setCourseStatus(userCourse.CourseStatus.T1);
                // Record Start Time and set to startTime attribute
                current_uCourse.setTimeStart(LocalDateTime.now());
                //courseTrackingService.incrementStartCount(data.getUserCourse());

                // Save to repo
                repo.save(current_uCourse);
                incrementStartCount(current_uCourse.getCourse().getCourse_id()); // Directly call method to increment start count
            }

            case T1 -> {
                current_uCourse.setCourseStatus(userCourse.CourseStatus.T2);

                // Save to repo
                repo.save(current_uCourse);
            }

            case T2 -> {
                current_uCourse.setCourseStatus(userCourse.CourseStatus.T3);
                repo.save(current_uCourse);
            }

            case T3 -> {
                current_uCourse.setCourseStatus(userCourse.CourseStatus.T4);
                repo.save(current_uCourse);
            }

            case T4 -> {
                current_uCourse.setCourseStatus(userCourse.CourseStatus.T5);
                repo.save(current_uCourse);
            }

            case T5 -> {
                current_uCourse.setCourseStatus(userCourse.CourseStatus.COMPLETE);
                // Record End Time
                current_uCourse.setTimeEnd(LocalDateTime.now());

                // Record End time and set to endTime attribute
                repo.save(current_uCourse);
            }



    }

        model.addAttribute("course", current_uCourse);

        //For comment section
        List<userCourse> userCoursesList = repo.findByCourseAndCommentIsNotNull(current_uCourse.getCourse());
        model.addAttribute("u_courses", userCoursesList);

        return "redirect:/viewCoursePage/{u_courseID}";
    }

    @Autowired
    UserServiceImpl userService;

    @RequestMapping("viewCoursePage/{u_courseID}")
    public String viewCourse(Principal principal, Model model, @PathVariable("u_courseID") Long u_courseID){
        User currentUser = userService.fetchUser(principal);
        UserDto currentUserDTO = userService.mapToUserDto(currentUser);

        //Get userCourse
        userCourse currentUCourse = repo.findById(u_courseID).get();

        //Add to model
        model.addAttribute("course", currentUCourse);

        //For comment section
        // Get list of comments on a specific course
        List<userCourse> userCoursesList = repo.findByCourseAndCommentIsNotNull(currentUCourse.getCourse());
        model.addAttribute("u_courses", userCoursesList);

        model.addAttribute("user", currentUserDTO);

        return "coursepage_v2";
    }

    @PostMapping("/commentSubmission/{u_courseID}")
    public String commentSubmission(@PathVariable ("u_courseID") Long u_courseID,
                                    @RequestParam("comment") String comment,
                                    @RequestParam("rate") int rating,
                                    Model model){

        // Get the current userCourse
        userCourse currentUCourse = repo.findById(u_courseID).get();

        // Handle the user rating
        // Database will show 1-star rating as 0 and 5-star rating as 4
        switch(rating) {
            case 1:
                currentUCourse.setRating(1);
                break;
            case 2:
                currentUCourse.setRating(2);
                break;
            case 3:
                currentUCourse.setRating(3);
                break;
            case 4:
                currentUCourse.setRating(4);
                break;
            case 5:
                currentUCourse.setRating(5);
                break;
            default:
                break;
        }

        // Set the comment to userCourse - taken from form in html
        currentUCourse.setComment(comment);

        // Save the user course
        repo.save(currentUCourse);


        // Add specific userCourse to model attribute
        model.addAttribute("course", currentUCourse);

        //For comment section
        // Get list of comments on a specific course
        List<userCourse> userCoursesList = repo.findByCourseAndCommentIsNotNull(currentUCourse.getCourse());

        // Add userCourses with the comments to model attribute
        model.addAttribute("u_courses", userCoursesList);

        return "redirect:/viewCoursePage/{u_courseID}";
    }


    @Autowired
    private CourseRepository CourseRepository;
    // Method to increment start count directly
    private void incrementStartCount(int courseId) {
        // Fetch the course from the database using courseId
        Optional<Course> optionalCourse = CourseRepository.findById(courseId);
        CourseTracking courseTracking = courseTrackingRepository.findByCourseId(courseId);
        // Check if the course exists
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            if (courseTracking == null) {
                // Create a new CourseTracking entity
                courseTracking = new CourseTracking();
                courseTracking.setCourseId(courseId);
                courseTracking.setStartCount(1);
                courseTracking.setCourseName(course.getName()); // Set the course name from the Course entity
            } else {
                int startCount = courseTracking.getStartCount();
                courseTracking.setStartCount(startCount+1);
            }
            // Save the CourseTracking entity
            courseTrackingRepository.save(courseTracking);
        }
    }


    @PostMapping("/updateFavourite/{u_courseID}")
    public String updateFavourite(@PathVariable ("u_courseID") Long u_courseID) {

        // Retrieve the current course
        userCourse currentUCourse = repo.findById(u_courseID).get();

        // Change favourite status to opposite what it was
        currentUCourse.setFavourite(!currentUCourse.isFavourite());

        // Save the updated userCourse in database
        repo.save(currentUCourse);

        // Redirect to course list page
        return "redirect:/viewCourses";
    }

}



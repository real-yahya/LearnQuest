package com.example.project_files.controller;

import com.example.project_files.dto.UserDto;
import com.example.project_files.entity.User;
import com.example.project_files.model.Badge;
import com.example.project_files.model.userCourse;
import com.example.project_files.repository.BadgeRepository;
import com.example.project_files.repository.RoleRepository;
import com.example.project_files.repository.UserCourseRepository;
import com.example.project_files.service.impl.BadgeService;
import com.example.project_files.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {


    @Autowired
    UserServiceImpl userService;

    @Autowired
    BadgeService badgeService;

    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    UserCourseRepository userCourseRepository;

    @Autowired
    private RoleRepository roleRepository;


    /*Handles the request to open the dashboard
     *After login use this controller method*/
    @RequestMapping("/dashboard")
    // Replace with UserDTO
    public String viewDashboard(Principal principal, Model model){

        // Get/Update current user
        User currentUser = userService.fetchUser(principal);

        // Handling the first log in
        userService.handleFirstLogin(principal.getName());

        // Update badges for user
        badgeService.checkBadge(currentUser);

        // Convert User to UserDto
        UserDto currentUserDTO = userService.mapToUserDto(currentUser);

        // Test if map was successful
        System.out.println(currentUserDTO.getFirstName());
        
        // For pie chart
        int cA = 0, cB = 0;

        for (userCourse u_course: userCourseRepository.findByUser(currentUser)){
            cA+=1;
            if(u_course.getCourseStatus()== userCourse.CourseStatus.COMPLETE){
                cB += 1;
            }
        }

        int valA = (int) ((cB / (double) cA) * 1000);
        int valB = 1000 - valA;

        System.out.println("Completed courses: "+cA + " " + "Courses:" + cB);
        System.out.println("Percentage of completed" + valA + " " + "Courses:" +valB);

        model.addAttribute("valA", valA);
        model.addAttribute("valB", valB);


        // Add to models
        model.addAttribute("user", currentUserDTO);
        model.addAttribute("courses", userCourseRepository.findByUser(currentUser));
        model.addAttribute("badges", badgeRepository.findByUserOwnContains(currentUser));



        return "dashboard_v2";
    }

    // Controller method for logout
    @GetMapping("/log-out")
    public String userLogout(){
        return "redirect:/";
    }

    // Handles the request to view courses page
    @RequestMapping("/viewCourses")
    public String viewCourses(Principal principal, Model model){
        //Get user
        User currentUser = userService.fetchUser(principal);

        //Get userCourses that user has
        Iterable<userCourse> courseList = userCourseRepository.findByUser(currentUser);


        // Convert User to UserDto
        UserDto currentUserDTO = userService.mapToUserDto(currentUser);

        //Add to model
        model.addAttribute("courses", courseList);
        model.addAttribute("user", currentUserDTO);

        return "course_list_page_v2";
    }


    // Handles requests to view badge page
    @RequestMapping("/viewBadges")
    public String viewBadges(Principal principal, Model model){
        //Get user
        User currentUser = userService.fetchUser(principal);

        //Find Badges for user
        List<Badge> userBadges = badgeRepository.findByUserOwnContains(currentUser);

        //Fetch badges that user doesn't own
        List<Badge> noBadgeList = new ArrayList<>();
        for (Badge badge : badgeRepository.findAll()) {
            if (!userBadges.contains(badge)){
                noBadgeList.add(badge);
            }
        }

        //Test to show badges user does not own
        for(Badge badge : noBadgeList){
            System.out.println(badge.getName());
        }

        // Convert User to UserDto
        UserDto currentUserDTO = userService.mapToUserDto(currentUser);

        //Pass model to html page
        model.addAttribute("user_badges", userBadges);
        model.addAttribute("no_badge", noBadgeList);
        model.addAttribute("user",currentUserDTO);

        return "badge";
    }

    @GetMapping("/analyticspage")
    public String showAnalyticsPage() {
        return "testanalytics";
    }

}

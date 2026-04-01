package com.example.project_files.controller;

import com.example.project_files.dto.UserCoursesCompletedDTO;
import com.example.project_files.dto.UserDailyChallengeDTO;
import com.example.project_files.dto.UserDto;
import com.example.project_files.entity.User;
import com.example.project_files.repo.UserDailyChallengeRepository;
import com.example.project_files.repository.UserCourseRepository;
import com.example.project_files.repository.UserRepository;
import com.example.project_files.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class LeaderboardController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDailyChallengeRepository userDailyChallengeRepository;

    @Autowired
    UserCourseRepository userCourseRepository;

    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/leaderboard")
    public String viewLeaderboard(Principal principal, Model model){

        User currentUser = userService.fetchUser(principal);
        UserDto currentUserDTO = userService.mapToUserDto(currentUser);

        // Pass data to html
        model.addAttribute("userDailyChallengeList", getUserDailyChallengesCompleted()); // UserDailyChallengesCompleted() returns list of users and number of challenges answered correctly
        model.addAttribute("userCoursesCompletedList", getUserCoursesCompleted()); // UserCoursesCompleted() returns list of users and number of courses completed
        model.addAttribute("user", currentUserDTO);

        return "leaderboard";
    }

    public List<UserDailyChallengeDTO> getUserDailyChallengesCompleted() {
        // Get data for challenges completed by user
        List<Object[]> result = userDailyChallengeRepository.countCorrectDailyChallengesByUserId();
        List<UserDailyChallengeDTO> users = new ArrayList<>(); // List of users for passing to html

        // SQL query to get correct answers by user returns a list of objects which need to be iterated through
        // UserID = first element and CorrectDailyChallengesCount = second element
        for (Object[] row : result) {
            Long userId = (Long) row[0];
            Long correctDailyChallengesCount = (Long) row[1];

            // Try to find user in database else go onto next iteration
            User currentUser = userRepository.findById(userId).orElse(null);
            if (currentUser == null) {
                continue;
            }

            // Data transfer object to make it simple to pass data as an object for each element
            UserDailyChallengeDTO userDTO = new UserDailyChallengeDTO(currentUser, correctDailyChallengesCount);
            users.add(userDTO);

        }
        return users;
    }

    public List<UserCoursesCompletedDTO> getUserCoursesCompleted() {
        // Get data for courses completed by user
        List<Object[]> result = userCourseRepository.countCoursesCompletedByUser();
        List<UserCoursesCompletedDTO> users = new ArrayList<>(); // List of users for passing to html

        // SQL query to get courses completed by user returns a list of objects which need to be iterated through
        // UserID = first element and coursesCompleted = second element
        for (Object[] row : result) {
            Long userId = (Long) row[0];
            Long coursesCompleted = (Long) row[1];

            // Try to find user in database else go onto next iteration
            User currentUser = userRepository.findById(userId).orElse(null);
            if (currentUser == null) {
                continue;
            }

            // Data transfer object to make it simple to pass data as an object for each element
            UserCoursesCompletedDTO userDTO = new UserCoursesCompletedDTO(currentUser, coursesCompleted);
            users.add(userDTO);

        }
        return users;
    }
}
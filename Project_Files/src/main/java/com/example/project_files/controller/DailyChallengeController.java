package com.example.project_files.controller;

import com.example.project_files.dto.UserDto;
import com.example.project_files.entity.User;
import com.example.project_files.model.Question;
import com.example.project_files.model.UserDailyChallenge;
import com.example.project_files.repo.QuestionRepository;
import com.example.project_files.repo.UserDailyChallengeRepository;
import com.example.project_files.service.QuestionService;
import com.example.project_files.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;

@Controller
public class DailyChallengeController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private UserDailyChallengeRepository userDailyChallengeRepo;

    @GetMapping ("/dailyChallenge")
    public String dailyChallenge(Principal principal, Model model) {
        //retrieve random question from database and add to model
        model.addAttribute("question", questionService.getRandomQuestion());

        // Get current user from session
        User currentUser = userService.fetchUser(principal);
        UserDto currentUserDTO = userService.mapToUserDto(currentUser);

        model.addAttribute("user", currentUserDTO);

        return "question";
    }

    @RequestMapping ("/submitQuestion")
    public String submitQuestion(Model model,
                                 Principal principal,
                                 @RequestParam int questionId,
                                 @RequestParam("selectedOption") String selectedOption) {
        // Get user submitting challenge
        User currentUser = userService.fetchUser(principal);
        model.addAttribute("user", currentUser); // Add user to model

        //retrieve question object from database to check answer
        Question question = questionService.getQuestionEntity(questionRepo.findById(questionId));

        System.out.println("Selected Option: " + selectedOption);
        System.out.println("Correct Answer: " + question.getAnswer());

        //Get current time
        LocalDateTime completionTime = LocalDateTime.now();

        //check if user answer matches, log this into database and redirect to appropriate page
        if ((Objects.equals(selectedOption, question.getAnswer())))
        {
            UserDailyChallenge currentChallenge = new UserDailyChallenge(currentUser, question, true, completionTime);
            userDailyChallengeRepo.save(currentChallenge);
            return "correct";
        }
        else
        {
            UserDailyChallenge currentChallenge = new UserDailyChallenge(currentUser, question, false, completionTime);
            userDailyChallengeRepo.save(currentChallenge);

            // Add selected option and question to model for displaying correct answer to user
            model.addAttribute("question", question);
            model.addAttribute("selectedOption", selectedOption);

            return "incorrect";
        }
    }

}

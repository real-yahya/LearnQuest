package com.example.project_files.controller;

import com.example.project_files.dto.UserDto;
import com.example.project_files.entity.User;
import com.example.project_files.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    // Constructor injection for UserService
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Handler for GET request to "/login" endpoint
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // Handler for GET request to "/register" endpoint
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user); // Add user object to the model
        return "register";
    }

    // Handler for POST request to "/register/save" endpoint
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               @RequestParam("role") String role,
                               BindingResult result,
                               Model model){
        // Check if there is already a user registered with the same email
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            // If user exists, add error message to the result
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        // If there are validation errors, return to registration form with error messages
        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        // Save the user if no validation errors
        userService.saveUser(userDto, role);
        return "redirect:/register?success";
    }

    // Handler for GET request to "/users" endpoint
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }


}

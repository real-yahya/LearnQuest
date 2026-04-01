package com.example.project_files.service.impl;

import com.example.project_files.dto.UserDto;
import com.example.project_files.entity.Role;
import com.example.project_files.entity.User;
import com.example.project_files.model.Course;
import com.example.project_files.model.userCourse;
import com.example.project_files.repository.CourseRepository;
import com.example.project_files.repository.RoleRepository;
import com.example.project_files.repository.UserCourseRepository;
import com.example.project_files.repository.UserRepository;
import com.example.project_files.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CourseService courseService;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserCourseRepository userCourseRepository;

    // Constructor injection for UserRepository, RoleRepository, and PasswordEncoder
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Method to save a new user
    @Override
    public void saveUser(UserDto userDto, String roleName) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // Encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Find or create the specified role
        Role userRole = roleRepository.findByName("ROLE_" + roleName.toUpperCase());
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("ROLE_" + roleName.toUpperCase());
            if (Objects.equals(userRole.getName(), "ROLE_ADMIN")){
                user.setAdmin(true);
            }

            roleRepository.save(userRole);
        }
        user.setRoles(Collections.singletonList(userRole));
        userRepository.save(user);
    }

    // Method to find a user by email
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Method to retrieve all users
    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    // Method to map User entity to UserDto
    public UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());

        if (user.isAdmin()){
            userDto.setAdmin(true);
        }
        else{
            userDto.setAdmin(false);
        }

        return userDto;
    }

    // Method to check if ROLE_ADMIN role exists, and create it if not
    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
    public void handleFirstLogin(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.isFirstLogin()) { // Check if it's the user's first login
            List<Course> courses = courseService.getAllCourses();
            courses.forEach(course -> {
                // Check if a UserCourse entry already exists for this user and course
                userCourse existingUserCourse = userCourseRepository.findByUserAndCourse(user, course);
                if (existingUserCourse == null) {
                    // Create a new UserCourse instance and save it
                    userCourseRepository.save(new userCourse(user, course, userCourse.CourseStatus.START));
                }
            });
            user.setFirstLogin(false); // Update first login flag
            userRepository.save(user); // Save the user entity
        }
    }



    public User fetchUser(Principal principal){
        //Get User object from principal
        //Done by matching the email
        User currentUser = userRepository.findByEmail(principal.getName());

        //Test to see if currentUser successfully passed
        System.out.println(currentUser.getName());

        return currentUser;
    }

}

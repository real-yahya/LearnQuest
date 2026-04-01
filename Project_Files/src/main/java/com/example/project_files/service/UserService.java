package com.example.project_files.service;

import com.example.project_files.dto.UserDto;
import com.example.project_files.entity.User;

import java.util.List;

public interface UserService {
    // Method to save a new user
    void saveUser(UserDto userDto, String role);

    // Method to find a user by email
    User findUserByEmail(String email);

    // Method to retrieve all users
    List<UserDto> findAllUsers();
}
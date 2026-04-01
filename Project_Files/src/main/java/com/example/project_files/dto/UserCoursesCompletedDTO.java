package com.example.project_files.dto;

import com.example.project_files.entity.User;

public class UserCoursesCompletedDTO {

    private User user;

    private Long coursesCompleted;

    public UserCoursesCompletedDTO(User user, Long coursesCompleted) {
        this.user = user;
        this.coursesCompleted = coursesCompleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCoursesCompleted() {
        return coursesCompleted;
    }

    public void setCoursesCompleted(Long coursesCompleted) {
        this.coursesCompleted = coursesCompleted;
    }
}

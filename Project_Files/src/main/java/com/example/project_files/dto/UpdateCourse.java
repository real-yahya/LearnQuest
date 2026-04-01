package com.example.project_files.dto;


import com.example.project_files.entity.User;
import com.example.project_files.model.userCourse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCourse {
    public long getUserCourse() {
        return userCourseID;
    }

    public void setUserCourse(int userCourseID) {
        this.userCourseID = userCourseID;
    }

    private long userCourseID;

    @Getter
    @Setter
    private long user;



}


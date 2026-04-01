package com.example.project_files.model;

import com.example.project_files.entity.User;
import jakarta.persistence.*;


import java.time.Duration;
import java.time.LocalDateTime;


@Entity
public class userCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int u_courseId;


    @ManyToOne

    private Course course;

    @ManyToOne

    private User user;

    private LocalDateTime timeStart;

    private LocalDateTime timeEnd;

    private Duration clearTime;

    private boolean isComplete;

    private CourseStatus courseStatus;
    public enum CourseStatus{
        START, T1, T2, T3, T4, T5,  COMPLETE
    }


    public userCourse(){
        this.favourite = false;
    }

    public userCourse(User user, Course course, CourseStatus status){
        this.user = user;
        this.course = course;
        this.courseStatus = status;
    }

    private boolean favourite;

    private String comment;

    private int rating;

    public long getU_courseId() {
        return u_courseId;
    }

    public void setU_courseId(int u_courseId) {
        this.u_courseId = u_courseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Duration getClearTime() {
        return clearTime;
    }

    public void setClearTime(Duration clearTime) {
        this.clearTime = clearTime;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}


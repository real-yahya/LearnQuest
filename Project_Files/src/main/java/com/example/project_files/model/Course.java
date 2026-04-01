package com.example.project_files.model;

import com.example.project_files.entity.CourseTracking;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    private int course_id;

    private String name;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    private CourseTracking courseTracking;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<userCourse> userCourses = new ArrayList<>();

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CourseTracking getCourseTracking() {
        return courseTracking;
    }

    public void setCourseTracking(CourseTracking courseTracking) {
        this.courseTracking = courseTracking;
    }
}

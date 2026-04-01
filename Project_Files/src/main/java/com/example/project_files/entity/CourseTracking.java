package com.example.project_files.entity;

import com.example.project_files.model.Course;
import jakarta.persistence.*;

@Entity
public class CourseTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int courseId;

    private int startCount;

    private String courseName;

    @OneToOne
    private Course course;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseID) {
        this.courseId = courseID;
    }

    public int getStartCount() {
        return startCount;
    }

    public void setStartCount(int startCount) {
        this.startCount = startCount;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return courseName;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

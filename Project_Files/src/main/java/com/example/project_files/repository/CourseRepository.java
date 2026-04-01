package com.example.project_files.repository;

import com.example.project_files.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository <Course, Integer> {
}

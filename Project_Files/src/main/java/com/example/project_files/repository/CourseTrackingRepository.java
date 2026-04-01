package com.example.project_files.repository;

import com.example.project_files.entity.CourseTracking;
import com.example.project_files.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseTrackingRepository extends CrudRepository<CourseTracking, Long> {
    CourseTracking findByCourseId(int courseId);

    //@Query("SELECT c FROM CourseTracking c ORDER BY c.startCount DESC")
    //List<CourseTracking> findAllOrderByStartCountDesc();
}
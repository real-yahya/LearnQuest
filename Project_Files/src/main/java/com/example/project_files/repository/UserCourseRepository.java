package com.example.project_files.repository;

import com.example.project_files.entity.User;
import com.example.project_files.model.Course;
import com.example.project_files.model.userCourse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserCourseRepository extends CrudRepository<userCourse, Long> {
    List<userCourse> findByUser(User user);

    userCourse findByUserAndCourse(User user, Course course);

    default Optional<userCourse> findByUser_IdAndCourse_Id(Long userId, Integer courseId) {
        return Optional.empty();
    }

    List<userCourse> findByCourseAndCommentIsNotNull(Course course);

    List<userCourse> findByCourse(Course course);

    @Query(value = "SELECT user_id, COUNT(u_course_Id) AS completed_courses " +
            "FROM co2201db_sprint2_final.user_course " +
            "WHERE course_Status = 6 " +
            "GROUP BY user_id " +
            "ORDER BY completed_courses DESC", nativeQuery = true)
    List<Object[]> countCoursesCompletedByUser();
}

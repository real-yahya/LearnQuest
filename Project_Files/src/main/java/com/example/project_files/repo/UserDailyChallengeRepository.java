package com.example.project_files.repo;

import com.example.project_files.model.UserDailyChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDailyChallengeRepository extends JpaRepository<UserDailyChallenge, Integer> {

    @Query(value = "SELECT user_id, COUNT(*) AS CorrectAnswersCount " +
            "FROM co2201db_Sprint2_final.user_daily_challenge " +
            "WHERE answered_correctly = 1 " +
            "GROUP BY user_id " +
            "ORDER BY correctAnswersCount DESC;", nativeQuery = true)
    List<Object[]> countCorrectDailyChallengesByUserId();
}

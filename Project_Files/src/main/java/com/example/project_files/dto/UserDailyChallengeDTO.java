package com.example.project_files.dto;

import com.example.project_files.entity.User;

public class UserDailyChallengeDTO {

    private User user;
    private Long correctDailyChallengesCount;

    public UserDailyChallengeDTO(User user, Long correctDailyChallengesCount) {
        this.user = user;
        this.correctDailyChallengesCount = correctDailyChallengesCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCorrectDailyChallengesCount() {
        return correctDailyChallengesCount;
    }

    public void setCorrectDailyChallengesCount(Long correctDailyChallengesCount) {
        this.correctDailyChallengesCount = correctDailyChallengesCount;
    }
}

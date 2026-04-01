package com.example.project_files.model;

import com.example.project_files.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserDailyChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Question question;

    private boolean answeredCorrectly;

    private LocalDateTime completionTime;

    public UserDailyChallenge() {
    }

    public UserDailyChallenge(User user, Question question, boolean answeredCorrectly, LocalDateTime completionTime) {
        this.user = user;
        this.question = question;
        this.answeredCorrectly = answeredCorrectly;
        this.completionTime = completionTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(boolean answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

    public LocalDateTime getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(LocalDateTime completionTime) {
        this.completionTime = completionTime;
    }
}

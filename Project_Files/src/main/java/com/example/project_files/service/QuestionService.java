package com.example.project_files.service;

import com.example.project_files.model.Question;
import com.example.project_files.repo.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepo;

    public Question getRandomQuestion() {
        //generate random integer within range of question count from database
        //Random randomInt = new Random();
        //int questionId =  randomInt.nextInt((int) questionRepo.count()) + 1;
        //return getQuestionEntity(questionRepo.findById(questionId));
        long questionCount = questionRepo.count();
        if (questionCount == 0) {
            throw new EntityNotFoundException("No questions found in the database.");
        }

        Random random = new Random();
        int randomQuestionId = random.nextInt((int) questionCount) + 1;

        Optional<Question> optionalQuestion = questionRepo.findById(randomQuestionId);
        if (optionalQuestion.isPresent()) {
            return optionalQuestion.get();
        } else {
            // Retry to get a random question recursively
            return getRandomQuestion();
        }
    }

    public Question getQuestionEntity(Optional<Question> optionalQuestion) {
        //This method return a Question object which isn't optional
        if (optionalQuestion.isPresent()) {
            return optionalQuestion.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

}

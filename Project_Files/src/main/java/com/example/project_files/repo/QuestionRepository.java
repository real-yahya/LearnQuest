package com.example.project_files.repo;

import com.example.project_files.model.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    //methods
}
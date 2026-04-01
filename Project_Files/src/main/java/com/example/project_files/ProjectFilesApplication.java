package com.example.project_files;

import com.example.project_files.model.Course;
import com.example.project_files.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.example.project_files.model.Question;
import com.example.project_files.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ProjectFilesApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProjectFilesApplication.class, args);
    }

    @Autowired
    private QuestionRepository questionRepo;
    @Override
    public void run(String... args) {
        if (questionRepo.count() == 0) {
            insertQuestions();
        }
    }

    public void insertQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question("Which of the following is not a property of a hashing function used in hash tables?", "Deterministic", "Fast computation", "Low collision probability", "Pre-image resistance", "Pre-image resistance"));
        questions.add(new Question("In the context of operating systems, what is the purpose of a semaphore?", "Synchronize access to shared resources", "Manage memory allocation", "Schedule processes", "Handle interrupts", "Synchronize access to shared resources"));
        questions.add(new Question("What is the primary purpose of Dijkstra's algorithm in computer science?", "Sorting", "Pathfinding", "Graph traversal", "Dynamic programming", "Pathfinding"));
        questions.add(new Question("Which sorting algorithm has an average and worst-case time complexity of O(n log n)?", "Bubble Sort", "Insertion Sort", "QuickSort", "Selection Sort", "QuickSort"));
        questions.add(new Question("Which data structure is best suited for implementing a priority queue?", "Stack", "Queue", "Heap", "Linked List", "Heap"));
        questions.add(new Question("What is the purpose of the TCP three-way handshake in networking?", "Data encryption", "Error correction", "Connection establishment", "Routing", "Connection establishment"));
        questions.add(new Question("In computer networks, what does the term 'subnetting' refer to?", "Assigning multiple IP addresses to a single device", "Dividing a large network into smaller, manageable sub-networks", "Encrypting data during transmission", "Configuring routers to handle multiple protocols", "Dividing a large network into smaller, manageable sub-networks"));

        questionRepo.saveAll(questions);
    }

}

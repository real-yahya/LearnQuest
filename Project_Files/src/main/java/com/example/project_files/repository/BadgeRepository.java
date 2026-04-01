package com.example.project_files.repository;

import com.example.project_files.entity.User;
import com.example.project_files.model.Badge;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeRepository extends CrudRepository<Badge, Integer> {

    // Find list of badges that the user owns
    public List<Badge> findByUserOwnContains(User user);

}

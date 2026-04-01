package com.example.project_files.model;

import com.example.project_files.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Badge {

    @Id
    @GeneratedValue
    private int Id;

    private String name;

    private String description;

    @ManyToMany
    private List<User> userOwn;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUserOwn() {
        return userOwn;
    }

    public void setUserOwn(List<User> userOwn) {
        this.userOwn = userOwn;
    }
}

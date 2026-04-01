package com.example.project_files.entity;

import com.example.project_files.model.userCourse;
import com.example.project_files.repository.RoleRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users") // Define the table name for this entity
public class User
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // User ID

    @Column(nullable=false)
    private String name; // User name

    @Column(nullable=false, unique=true)
    private String email; // User email

    @Column(nullable=false)
    private String password; // User password

    @Column(name = "first_login", nullable = false)
    private boolean firstLogin = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL) // Define many-to-many relationship with Role entity
    @JoinTable(
            name="users_roles", // Define join table name
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>(); // List of roles associated with this user

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<userCourse> userCourses = new ArrayList<>();


    private boolean isAdmin;

    // All getters and setters


    public List<userCourse> getUserCourses() {
        return userCourses;
    }

    public void setUserCourses(List<userCourse> userCourses) {
        this.userCourses = userCourses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }



    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
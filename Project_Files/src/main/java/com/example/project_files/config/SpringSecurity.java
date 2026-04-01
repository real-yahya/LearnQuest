package com.example.project_files.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    // Autowire UserDetailsService to retrieve user details for authentication
    @Autowired
    private UserDetailsService userDetailsService;

    // Define a bean for PasswordEncoder to encrypt passwords
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Configure security filters for HTTP requests
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                                // Define authorization rules for different endpoints
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/dashboard/**").permitAll()
                                .requestMatchers("/users").hasRole("ADMIN")
                                .requestMatchers("/viewCourses").permitAll()
                                .requestMatchers("/updateCourse/**").permitAll()
                                .requestMatchers("/viewBadges").permitAll()
                                .requestMatchers("/dailyChallenge").permitAll()
                                .requestMatchers("/submitQuestion").permitAll()
                                .requestMatchers("/popularpage").permitAll()
                                .requestMatchers("/popular").permitAll()
                                .requestMatchers("/leaderboard").permitAll()
                                .requestMatchers("/viewCoursePage/**").permitAll()
                                .requestMatchers("/updateFavourite/**").permitAll()
                                .requestMatchers("/commentSubmission/**").permitAll()
                                .requestMatchers("/course-direct/**").permitAll()
                                .requestMatchers("/testanalytics").hasRole("ADMIN")


                ).formLogin(
                        // Configure form authentication
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/dashboard")
                                .permitAll()
                ).logout(
                        // Configure logout settings
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build(); // Build and return the configured HttpSecurity object
    }

    // Configure global authentication mechanism
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService) // Set the userDetailsService for authentication
                .passwordEncoder(passwordEncoder()); // Set the password encoder
    }
}

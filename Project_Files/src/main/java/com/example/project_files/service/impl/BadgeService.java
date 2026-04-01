package com.example.project_files.service.impl;

import com.example.project_files.entity.User;
import com.example.project_files.model.Badge;
import com.example.project_files.model.userCourse;
import com.example.project_files.repository.BadgeRepository;
import com.example.project_files.repository.UserCourseRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;



@Service
public class BadgeService {

    @Autowired
    public BadgeRepository badgeRepository;

    @Autowired
    public UserCourseRepository userCourseRepository;

    @PostConstruct
    private void createBadge(){
        // Condition to check if badges have been generated already or not
        if (badgeRepository.count() == 0){

            // Badge for first login
            Badge badge1 = new Badge();
            badge1.setName("Prodigy");
            badge1.setDescription("Login for the first time");
            badge1.setUserOwn(new ArrayList<>());
            badgeRepository.save(badge1);

            // Badge for first course
            Badge badge2 = new Badge();
            badge2.setName("First Step");
            badge2.setDescription("Complete your first course");
            badge2.setUserOwn(new ArrayList<>());
            badgeRepository.save(badge2);

            // Badge for two course completion
            Badge badge3 = new Badge();
            badge3.setName("The Two-Time");
            badge3.setDescription("Complete two courses");
            badge3.setUserOwn(new ArrayList<>());
            badgeRepository.save(badge3);

            // Badge for time clear
            Badge badge4 = new Badge();
            badge4.setName("Speedster");
            badge4.setDescription("Complete a course in under 2hrs");
            badge4.setUserOwn(new ArrayList<>());
            badgeRepository.save(badge4);

        }
    }

    public void checkBadge(User user){

        //Get all badges current user has
        List<Badge> userBadges = badgeRepository.findByUserOwnContains(user);

        //If user doesn't have login badge = it's the user's first login
        if (userBadges.isEmpty()){
            Badge firstLogin = badgeRepository.findById(1).get();
            userBadges.add(firstLogin);
            firstLogin.getUserOwn().add(user);
            badgeRepository.save(firstLogin);
        }

        //Get all possible badges
        Iterable<Badge> allBadges = badgeRepository.findAll();

        //BadgeID List
        List<Integer> noBadgeIDs = new ArrayList<>();

        //Get badgeIDs of all badges that user doesn't own
        for (Badge badge : allBadges) {
            for(Badge uBadge : userBadges) {
                if (badge.getId() != uBadge.getId()) {
                    noBadgeIDs.add(badge.getId());
                }
            }
        }

        for (int i = 0; i < noBadgeIDs.size(); i++) {
            System.out.println(noBadgeIDs.get(i));
        }



        // Use switch case to check badge conditions
        for (int ID : noBadgeIDs){
            switch (ID) {
                //No switch case for one since that's the unique id for login badge which is already given to the user

                // Badge condition for first ever course completion
                case 2:
                // Get userCourses relating to current user
                for (userCourse uc : userCourseRepository.findByUser(user)) {

                    // For testing purposes - remove after final implementation
                    System.out.println("TEST : "+ uc.getCourse().getName());

                    // If a user's course has been set to complete, add the badge and escape loop
                    if (uc.getCourseStatus() == userCourse.CourseStatus.COMPLETE) {
                        //Fetch the badge that the user is yet to have
                        Badge currentBadge = badgeRepository.findById(2).get();

                        // Get list of users who own the badge and add current user
                        currentBadge.getUserOwn().add(user);
                        badgeRepository.save(currentBadge);
                        break;
                    }
                }
                    break;

                case 3:
                    // Same as case 2, but checks if more than one is set to complete
                    int counter = 0;
                    // Get userCourses relating to current user
                    for (userCourse uc : userCourseRepository.findByUser(user)) {
                        // For testing purposes
                        System.out.println("TEST : "+ uc.getCourse().getName());
                        // Replaced with courseStatus rather than isComplete
                        if (uc.getCourseStatus() == userCourse.CourseStatus.COMPLETE) {
                            counter += 1;
                        }
                    }

                    if (counter == 2) {
                        //Fetch the badge that the user is yet to have
                        Badge currentBadge = badgeRepository.findById(3).get();

                        // Get list of users who own the badge and add current user
                        currentBadge.getUserOwn().add(user);
                        badgeRepository.save(currentBadge);
                    }
                    break;

                // Badge condition for fast clear time
                case 4:
                    for (userCourse uc : userCourseRepository.findByUser(user)) {
                        // If a course is complete, both recorded time must be available
                        // Checks if a course is completed
                        if (uc.getCourseStatus() == userCourse.CourseStatus.COMPLETE){
                            // Finds difference between start and end
                            Duration clearTime = Duration.between(uc.getTimeStart(), uc.getTimeEnd());
                            // Must be under 2 hours to receive badge
                            Duration thresholdTime = Duration.ofHours(2);

                            // Check if clear time is under 2 hours
                            if (clearTime.compareTo(thresholdTime) < 0){
                                Badge timeBadge = badgeRepository.findById(4).get();
                                timeBadge.getUserOwn().add(user);
                                badgeRepository.save(timeBadge);
                            }
                        }
                    }
                    break;

                default:
                    break;

            }

        }




    }

}

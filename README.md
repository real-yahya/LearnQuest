# LearnQuest

> A full-stack gamified learning platform built with Java Spring Boot and MySQL — featuring course progress tracking, daily challenges, leaderboards, badges, ratings, and admin analytics.

---

## Overview

LearnQuest is a web-based e-learning platform developed as a university group project following Agile (Scrum) methodology across two sprints. Users can browse and complete courses, track their progress, compete on a global leaderboard, earn badges, and rate content — while admins get a powerful analytics dashboard to monitor platform engagement.

---

## Features

- **User Authentication & Roles** — Secure registration and login with Spring Security. Role-based access separates standard users from admins.
- **Course System** — Browse, start, and complete courses with guided step-by-step content.
- **Progress Bar** — Visual progress tracking as users move through course material and complete tasks.
- **Daily Challenges** — Quiz-style daily challenges that contribute to the global leaderboard score.
- **Global Leaderboard** — Ranks users by daily challenge performance, updating in real time.
- **Favourites** — Bookmark courses with a heart icon and access them from a dedicated favourites tab.
- **Popular Courses** — A dynamically ranked page showing the most-started courses, updated as users engage.
- **Comments & Ratings** — Rate and review courses after completion. Validation ensures only completed courses can be reviewed, and a rating is required before posting.
- **Badges** — Achievements awarded based on activity milestones.
- **Admin Analytics Dashboard** — Admins can view platform-wide engagement data including started and rated courses. Hidden from standard users.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 19, Spring Boot |
| Security | Spring Security |
| Database | MySQL 8.0 |
| Frontend | Thymeleaf, HTML, CSS |
| Build Tool | Gradle |
| Architecture | MVC (Model-View-Controller) |

---

## Project Structure

```
src/
├── main/
│   ├── java/com/example/project_files/
│   │   ├── config/          # Spring Security configuration
│   │   ├── controller/      # MVC controllers (Auth, Course, Dashboard, Leaderboard, etc.)
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # JPA entities (User, Role, CourseTracking)
│   │   ├── model/           # Domain models (Course, Badge, Question, etc.)
│   │   ├── repository/      # Spring Data JPA repositories
│   │   ├── security/        # Custom user details service
│   │   └── service/         # Business logic layer
│   └── resources/
│       ├── templates/       # Thymeleaf HTML templates
│       └── static/          # Images and static assets
```

---

## Getting Started

### Prerequisites

- [Java 19 (OpenJDK)](https://jdk.java.net/19/)
- [MySQL Community Server 8.0](https://dev.mysql.com/downloads/mysql/)
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)

### Database Setup

1. Open MySQL Workbench and create a new connection using the **+** button next to "MySQL Connections"
2. Give the connection a name, set the username to `root` and password to `password`
3. Click **Test Connection** to verify, then save

### Running the Application

1. Clone or download this repository
2. Open the `Project_Files` folder as the root in IntelliJ IDEA
3. Set the JDK to **OpenJDK 19** via `File > Project Structure > Project Settings > Project`
4. Run `ProjectFilesApplication.java`
5. Wait ~10 seconds, then visit: [http://localhost:8080/login](http://localhost:8080/login)

---

## Testing

Testing documentation is in the `/Testing` directory, covering:

- Login & Registration system
- Dashboard page
- Popular Courses page
- Progress Bar
- Badge system

---

## My Contribution

**Yahya Mohamed (ym196)** — Login Page (Frontend) & Progress Bar (Design + Implementation)

**Login Page:** Built the frontend for the login and registration system, designing the UI and ensuring a clean, user-friendly entry point into the platform.

**Progress Bar:** Designed and implemented the progress bar feature, allowing users to track how far they have completed a course.

---

## Full Team

Built by a team of 9 developers working in an Agile Scrum environment across 2 sprints.

| Feature | Developer |
|---|---|
| **Login Page & Progress Bar** | **Yahya Mohamed (ym196)** |
| Dashboard Revision | ar581 |
| Favourites Tab | aa1376 |
| Comments & Ratings | yz658 |
| Popular Courses | jgs23 |
| Global Leaderboard | ap828, anaas1 |
| Admin Analytics | vm189, mu68 |

---

## License

Developed for academic purposes at the University of Leicester.

# 🧑‍🤝‍🧑 MYDAILY – Social Network Web App

A full-stack social networking application built with **Spring Boot (modular-monolith)** for the backend and **ReactJS** for the frontend.

---

## 🌐 Features

### 👥 Auth (MySQL)
- User registration and login (JWT)
- Logout (client-side token removal)
- Role-based authorization (`ROLE_USER`, `ROLE_ADMIN`)
- Refresh token (optional)

### 👤 User (MySQL)
- View and update user profile
- Change password
- Upload avatar
- Search users by name or email

### 📝 Post (MySQL)
- Create posts with text and image
- View personal and friends’ posts
- Edit and delete posts
- Pagination and sorting by timestamp

### 💬 Comment (MySQL)
- Comment on posts
- Nested replies (optional)
- Soft delete comments
- Count comments on a post

### 👍 Like (MySQL)
- Like / unlike a post
- Count likes on a post
- View users who liked a post

### 🤝 Friend (MySQL)
- Send / accept / reject friend requests
- View friend list
- View pending requests
- Unfriend users

### 💬 Chat (MongoDB)
- 1:1 messaging
- Realtime chat with WebSocket + STOMP
- Message types: text, emoji, basic image
- View chat history

### 🔔 Notification (MongoDB)
- Realtime notifications via WebSocket
- Events: new comment, like, message, friend request
- Mark as read / delete notification

### 🔎 Search / Feed (MySQL)
- Search users or posts by keywords
- News feed from friends (ordered by time)
- Suggested friends (basic mutual friend logic)

### ⚙️ Admin Panel (MySQL)
- View / manage users (e.g. ban)
- Stats: new users, posts, etc.
- Simple dashboard (optional)

---

## 🛠️ Tech Stack

| Layer       | Technology |
|-------------|------------|
| Backend     | Spring Boot, Spring Security, Spring Web, Spring Data JPA, Spring Modulith|
| Frontend    | ReactJS, TailwindCSS |
| Auth        | JWT, OAuth2 |
| Database    | MySQL (relational), MongoDB (NoSQL) |
| Realtime    | WebSocket, STOMP |
| Build Tool  | Maven (multi-module) |
| Docs        | Swagger / OpenAPI |
| Dev Tools   | Docker, Postman, IntelliJ Ultimate |
| Testing     | JUnit 5, Mockito |

---

## 🧱 Architecture

> **Modular-Monolith** using [Spring Modulith](https://spring.io/projects/spring-modulith)

- Each domain (auth, post, user, chat...) is a standalone module.
- Clean separation with `@ApplicationModule` in `package-info.java`.
- Internal module communication via service layer.



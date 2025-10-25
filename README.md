# 🚀 Recruitment Management System - Backend

A Spring Boot REST API for managing recruitment workflows with role-based access control.

**Tech Stack:** Java 22 ☕ | Spring Boot 🍃 | MySQL 🗄️ | JWT 🔑

---

## 📖 About

This backend enables:
- **Applicants** 👤 to upload resumes and apply for jobs
- **Admins** 🧑‍💼 to create job postings and review applications
- **Secure authentication** 🔒 with JWT tokens

---

## ✨ Features

### 👤 For Applicants
- 🔐 Secure signup and login
- 📤 Upload resume (PDF/DOCX)
- 💼 Browse and apply to jobs
- 📊 Track applications

### 🧑‍💼 For Admins
- ✏️ Create and manage job postings
- 👥 View all applicants
- 🔍 Review detailed applicant profiles
- 📈 Track applications per job

---

## 🛠️ Tech Stack

- ☕ **Java 22**
- 🍃 **Spring Boot 3.x**
- 🔒 **Spring Security**
- 🗄️ **MySQL 8.0**
- 🔑 **JWT Authentication**
- 📦 **Hibernate/JPA**
- 🔧 **Maven**

---

## 🚀 Getting Started

### Prerequisites

- ☕ Java 22+
- 🗄️ MySQL 8.0+
- 🔧 Maven 3.8+

### Installation

**1️⃣ Clone the repository**
git clone https://github.com/Arsalan-462/recruitment-system-backend.git
cd recruitment-system-backend


**2️⃣ Create database**
CREATE DATABASE recruitment_db;


**3️⃣ Configure application.properties**
spring.datasource.url=jdbc:mysql://localhost:3306/recruitment_db
spring.datasource.username=your_username
spring.datasource.password=your_password
server.port=8081


**4️⃣ Run the application**
mvn spring-boot:run


✅ Server starts at: `http://localhost:8081`

---

## 📚 API Endpoints

### 🔐 Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/signup` | Register new user |
| POST | `/login` | Login and get JWT token |

### 👤 Applicant Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/uploadResume` | Upload resume file 📤 |
| GET | `/jobs` | View all jobs 💼 |
| GET | `/jobs/apply?job_id={id}` | Apply to a job ✅ |

**Auth Required:** 🔑 Bearer Token (Applicant)

### 🧑‍💼 Admin Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/admin/job` | Create new job posting ✏️ |
| GET | `/admin/applicants` | View all applicants 👥 |
| GET | `/admin/applicant/{id}` | View applicant details 🔍 |
| GET | `/admin/job/{id}` | View job with applicants 📊 |

**Auth Required:** 🔑 Bearer Token (Admin)

---

## 🧪 Quick Test Guide

### 1️⃣ **Create Applicant**
POST http://localhost:8081/signup

{
"name": "John Doe",
"email": "john@example.com",
"password": "password123",
"address": "Delhi",
"userType": "Applicant",
"profileHeadline": "Java Developer"
}


### 2️⃣ **Login**
POST http://localhost:8081/login

{
"email": "john@example.com",
"password": "password123"
}


Response:
{
"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}


### 3️⃣ **Use Token in Headers**
Authorization: Bearer <your_token_here>


### 4️⃣ **Upload Resume**
POST http://localhost:8081/uploadResume
Authorization: Bearer <token>
Content-Type: multipart/form-data

file: [Select PDF/DOCX file]


---

## 📁 Project Structure
src/main/java/com/example/recruitmentsystem/
├── 🎮 controller/ # REST API Controllers
├── 📦 model/ # Database Entities
├── 💾 repository/ # Data Access Layer
├── ⚙️ service/ # Business Logic
├── 🔒 security/ # JWT & Security Config
└── 📋 dto/ # Data Transfer Objects


---

## 🔒 Security Features

- 🔐 BCrypt password encryption
- ⏰ JWT tokens expire in 24 hours
- 🛡️ Role-based access control
- ✅ File type validation
- 🚫 CSRF protection for stateless API

---

## 🗄️ Database Schema

### Users Table
- 👤 User information
- 🔑 Email (unique)
- 🔒 Encrypted password
- 🏷️ Role (Applicant/Admin)

### Profiles Table
- 📄 Skills, education, experience
- 📞 Contact information
- 📎 Resume file path

### Jobs Table
- 💼 Job title and description
- 🏢 Company name
- 👨‍💼 Posted by (Admin)
- 📊 Application count

### Job_Applicants Table
- 🔗 Links jobs and applicants
- 📝 Tracks applications

---

## 📝 Notes

- 📌 Use `userType: "Admin"` (capital A) for admin users
- 📌 Use `userType: "Applicant"` for regular users
- 📌 Tokens must be refreshed after 24 hours
- 📌 Only PDF and DOCX files accepted for resumes

---

## 👨‍💻 Author

**Your Name**
- 💻 GitHub: Arsalan-462
- 📧 Email: md.arsalan462@gmail.com

---

<div align="center">

### ⭐ Star this repo if you find it helpful!

Made with ❤️ and ☕

</div>

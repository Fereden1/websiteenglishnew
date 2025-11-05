package org.example.websiteenglish.entity;

import java.sql.Timestamp;

public class Application {
    private int id;
    private int userId;
    private String courseType;
    private String courseIdentifier; // Конкретный идентификатор курса (например, "conversational-basic")
    private String studentName;
    private String email;
    private String phone;
    private String message;
    private String status;

    public Application() {}

    public Application(int userId, String courseType, String studentName, String email, String phone, String message) {
        this.userId = userId;
        this.courseType = courseType;
        this.studentName = studentName;
        this.email = email;
        this.phone = phone;
        this.message = message;
        this.status = "NEW";
    }

    // Геттеры и сеттеры для всех полей
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getCourseType() { return courseType; }
    public void setCourseType(String courseType) { this.courseType = courseType; }
    public String getCourseIdentifier() { return courseIdentifier; }
    public void setCourseIdentifier(String courseIdentifier) { this.courseIdentifier = courseIdentifier; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

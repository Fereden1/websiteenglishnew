package org.example.websiteenglish.entity;

import java.sql.Timestamp;

public class Comment {
    private int id;
    private int userId;
    private String course;
    private String text;
    private Timestamp createdAt;
    private String userName;

    public Comment() {}

    public Comment(int userId, String course, String text) {
        this.userId = userId;
        this.course = course;
        this.text = text;
    }

    // getters/setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}

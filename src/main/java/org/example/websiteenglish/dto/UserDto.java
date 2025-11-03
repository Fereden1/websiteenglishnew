package org.example.websiteenglish.dto;

public class UserDto {
    private String email;
    private String name;

    public UserDto(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
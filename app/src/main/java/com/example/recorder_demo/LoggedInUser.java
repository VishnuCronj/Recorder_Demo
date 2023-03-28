package com.example.recorder_demo;

public class LoggedInUser {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String accessToken;
    private String refreshToken;

    public LoggedInUser(String email, String username, String firstName, String lastName, String accessToken, String refreshToken) {
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}

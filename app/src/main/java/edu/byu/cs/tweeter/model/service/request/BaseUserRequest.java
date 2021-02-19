package edu.byu.cs.tweeter.model.service.request;

public abstract class BaseUserRequest {

    private final String username;
    private final String password;

    public BaseUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

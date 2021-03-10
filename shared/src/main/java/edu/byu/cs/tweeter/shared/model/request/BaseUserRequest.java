package edu.byu.cs.tweeter.shared.model.request;

public abstract class BaseUserRequest {

    private String username;
    private String password;

    public BaseUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public BaseUserRequest() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

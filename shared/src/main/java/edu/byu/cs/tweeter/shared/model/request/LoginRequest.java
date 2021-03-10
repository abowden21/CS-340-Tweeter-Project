package edu.byu.cs.tweeter.shared.model.request;

/**
 * Contains all the information needed to make a login request.
 */
public class LoginRequest extends BaseUserRequest {

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        super(username, password);
    }
}

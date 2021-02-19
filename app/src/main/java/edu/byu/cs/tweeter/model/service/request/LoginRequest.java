package edu.byu.cs.tweeter.model.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class LoginRequest extends BaseUserRequest {

    public LoginRequest(String username, String password) {
        super(username, password);
    }
}

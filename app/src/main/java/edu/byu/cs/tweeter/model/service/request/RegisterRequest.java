package edu.byu.cs.tweeter.model.service.request;

import android.util.Base64;

public class RegisterRequest extends BaseUserRequest {

    private String firstName;
    private String lastName;
    private String imageBase64String;

    public RegisterRequest(String username, String password, String firstName, String lastName, byte[] imageBytes) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageBase64String = Base64.encodeToString(imageBytes, 0);
    }
}

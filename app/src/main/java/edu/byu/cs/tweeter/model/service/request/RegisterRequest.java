package edu.byu.cs.tweeter.model.service.request;

import android.util.Base64;

public class RegisterRequest extends BaseUserRequest {

    private String firstName;
    private String lastName;
//    private byte[] imageBytes;

    private String imageBase64String;

    public RegisterRequest(String username, String password, String firstName, String lastName, byte[] imageBytes) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
//        this.imageBytes = imageBytes;
        this.imageBase64String = Base64.encodeToString(imageBytes, 0);
    }
}

package edu.byu.cs.tweeter.shared.model.request;

import java.util.Base64;

public class RegisterRequest extends BaseUserRequest {

    private String firstName;
    private String lastName;
    private String imageBase64String;

    public RegisterRequest(){}

    public RegisterRequest(String username, String password, String firstName, String lastName, byte[] imageBytes) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageBase64String = Base64.getEncoder().encodeToString(imageBytes);
    }

    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageBase64String() {
        return this.imageBase64String;
    }
    public void setImageBase64String(String imageBase64String) {
        this.imageBase64String = imageBase64String;
    }
}

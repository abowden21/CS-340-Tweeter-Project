package edu.byu.cs.tweeter.shared.model.request;

public class PostStatusRequest {

    private String message;
    private String authToken;

    public PostStatusRequest() {}
    public PostStatusRequest(String authToken, String message) {

        this.authToken = authToken;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthToken() {
        return this.authToken;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}

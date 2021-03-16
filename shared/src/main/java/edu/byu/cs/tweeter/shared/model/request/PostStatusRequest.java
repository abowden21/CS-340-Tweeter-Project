package edu.byu.cs.tweeter.shared.model.request;

import edu.byu.cs.tweeter.shared.model.domain.AuthToken;

public class PostStatusRequest {

    private String message;
    private AuthToken authToken;

    public PostStatusRequest() {}
    public PostStatusRequest(AuthToken authToken, String message) {

        this.authToken = authToken;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public AuthToken getAuthToken() {
        return this.authToken;
    }
    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}

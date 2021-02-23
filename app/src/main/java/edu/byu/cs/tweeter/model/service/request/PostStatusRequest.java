package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class PostStatusRequest {

    private String message;
    private AuthToken authToken;

//    public PostStatusRequest() {}
    public PostStatusRequest(AuthToken authToken, String message) {

        this.authToken = authToken;
        this.message = message;
    }
}

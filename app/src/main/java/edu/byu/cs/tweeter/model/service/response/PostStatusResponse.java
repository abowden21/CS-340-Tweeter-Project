package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusResponse extends Response {
    private Status status;

    public PostStatusResponse(boolean success) {
        super(success);
    }

    public PostStatusResponse(boolean success, String message) {
        super(success, message);
    }
}

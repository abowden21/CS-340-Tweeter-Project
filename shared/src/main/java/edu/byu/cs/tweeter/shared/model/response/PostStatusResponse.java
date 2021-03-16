package edu.byu.cs.tweeter.shared.model.response;

import edu.byu.cs.tweeter.shared.model.domain.Status;

public class PostStatusResponse extends Response {
    private Status status;

    public PostStatusResponse(){}

    public PostStatusResponse(Status status) {
        super(true);
        this.status = status;
    }

    public PostStatusResponse(String message) {
        super(false, message);
    }

    public Status getStatus() {
        return this.status;
    }
    public void setStatue(Status status) {
        this.status = status;
    }
}

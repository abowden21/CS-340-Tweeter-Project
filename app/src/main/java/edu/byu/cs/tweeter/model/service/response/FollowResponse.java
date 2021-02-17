package edu.byu.cs.tweeter.model.service.response;

public class FollowResponse extends Response {
    boolean isFollowResponse;

    public FollowResponse(boolean success, boolean isFollowResponse) {
        super(success);
        this.isFollowResponse = isFollowResponse;
    }

    public FollowResponse(boolean success, boolean isFollowResponse, String message) {
        super(success, message);
        this.isFollowResponse = isFollowResponse;
    }

    public boolean isFollowResponse() {
        return isFollowResponse;
    }
}

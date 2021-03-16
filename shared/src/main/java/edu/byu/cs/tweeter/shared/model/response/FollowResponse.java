package edu.byu.cs.tweeter.shared.model.response;

public class FollowResponse extends Response {
    boolean isFollowResponse;

    public FollowResponse() {}

    public FollowResponse(boolean success, boolean isFollowResponse) {
        super(success);
        this.isFollowResponse = isFollowResponse;
    }

    public FollowResponse(boolean success, String message) {
        super(success, message);
    }

    public boolean isFollowResponse() {
        return isFollowResponse;
    }

    public void setFollowResponse(boolean followResponse) {
        this.isFollowResponse = followResponse;
    }
}

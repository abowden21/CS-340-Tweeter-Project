package edu.byu.cs.tweeter.shared.model.response;

public class FollowResponse extends Response {
    private boolean followResponse;

    public FollowResponse() {}

    public FollowResponse(boolean success, boolean followResponse) {
        super(success);
        this.followResponse = followResponse;
    }

    public FollowResponse(boolean success, String message) {
        super(success, message);
    }

    public boolean isFollowResponse() {
        return followResponse;
    }

    public void setFollowResponse(boolean followResponse) {
        this.followResponse = followResponse;
    }
}

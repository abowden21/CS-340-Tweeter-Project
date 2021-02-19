package edu.byu.cs.tweeter.model.service.response;

public class FollowStatusResponse extends Response {
    private boolean isFollowingUser;


    public FollowStatusResponse(boolean isFollowingUser, boolean success) {
        super(success);
        this.isFollowingUser = isFollowingUser;

    }

    public FollowStatusResponse(boolean isFollowingUser, boolean success, String message) {
        super(success, message);
        this.isFollowingUser = isFollowingUser;
    }

    public boolean isFollowingUser() {
        return isFollowingUser;
    }
}

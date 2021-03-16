package edu.byu.cs.tweeter.shared.model.response;

public class FollowStatusResponse extends Response {
    private boolean followingUser;

    public FollowStatusResponse() {

    }

    public FollowStatusResponse(boolean followingUser, boolean success) {
        super(success);
        this.followingUser = followingUser;

    }

    public FollowStatusResponse(boolean success, String message) {
        super(success, message);
    }

    public boolean isFollowingUser() {
        return followingUser;
    }

    public void setFollowingUser(boolean followingUser) {
        this.followingUser = followingUser;
    }
}

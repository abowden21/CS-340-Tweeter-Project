package edu.byu.cs.tweeter.shared.model.response;

public class FollowStatusResponse extends Response {
    private boolean isFollowingUser;

    public FollowStatusResponse() {

    }

    public FollowStatusResponse(boolean isFollowingUser, boolean success) {
        super(success);
        this.isFollowingUser = isFollowingUser;

    }

    public FollowStatusResponse(boolean success, String message) {
        super(success, message);
    }

    public boolean isFollowingUser() {
        return isFollowingUser;
    }

    public void setFollowingUser(boolean followingUser) {
        isFollowingUser = followingUser;
    }
}

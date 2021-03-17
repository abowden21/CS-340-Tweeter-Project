package edu.byu.cs.tweeter.shared.model.response;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FollowStatusResponse)) return false;
        FollowStatusResponse that = (FollowStatusResponse) o;
        return isFollowingUser() == that.isFollowingUser() && isSuccess() == that.isSuccess()
                && getMessage() == that.getMessage();
    }
}

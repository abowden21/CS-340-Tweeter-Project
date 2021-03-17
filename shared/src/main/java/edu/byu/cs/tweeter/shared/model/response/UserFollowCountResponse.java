package edu.byu.cs.tweeter.shared.model.response;

import java.util.Objects;

public class UserFollowCountResponse extends Response {

    private int followers;
    private int followees;

    public UserFollowCountResponse() {
    }

    public UserFollowCountResponse(String message) {
        super(false, message);
    }

    public UserFollowCountResponse(int followers, int followees) {
        super(true);
        this.followers = followers;
        this.followees = followees;
    }

    public int getFollowers() {
        return this.followers;
    }

    public int getFollowees() {
        return this.followees;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void setFollowees(int followees) {
        this.followees = followees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFollowCountResponse)) return false;
        UserFollowCountResponse that = (UserFollowCountResponse) o;
        return getFollowers() == that.getFollowers() &&
                getFollowees() == that.getFollowees() && isSuccess() == that.isSuccess()
                && getMessage() == that.getMessage();
    }
}

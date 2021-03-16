package edu.byu.cs.tweeter.shared.model.response;

public class UserFollowCountResponse extends Response {

    private int followers;
    private int followees;

    public UserFollowCountResponse() {}

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
    public int getFollowees() { return this.followees; }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void setFollowees(int followees) {
        this.followees = followees;
    }
}

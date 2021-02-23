package edu.byu.cs.tweeter.model.service.response;

public class GetFolloweeCountResponse extends Response {

    private int followees;

    GetFolloweeCountResponse(String message) {
        super(false, message);
    }

    public GetFolloweeCountResponse(int followees) {
        super(true);
        this.followees = followees;
    }

    public int getFollowees() {
        return this.followees;
    }
}

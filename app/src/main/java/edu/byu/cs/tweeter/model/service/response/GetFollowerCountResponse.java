package edu.byu.cs.tweeter.model.service.response;

public class GetFollowerCountResponse extends Response {

    private int followers;

    GetFollowerCountResponse(String message) {
        super(false, message);
    }

    public GetFollowerCountResponse(int followers) {
        super(true);
        this.followers = followers;
    }

    public int getFollowers() {
        return this.followers;
    }
}

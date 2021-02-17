package edu.byu.cs.tweeter.model.service.request;

public class FollowRequest {
    boolean isFollowRequest;
    private String userAlias;
    private String followerAlias;

    public FollowRequest(boolean isFollowRequest, String userAlias, String followerAlias) {
        this.isFollowRequest = isFollowRequest;
        this.userAlias = userAlias;
        this.followerAlias = followerAlias;
    }

    public boolean isFollowRequest() {
        return isFollowRequest;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public String getFollowerAlias() {
        return followerAlias;
    }
}

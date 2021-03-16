package edu.byu.cs.tweeter.shared.model.request;

import edu.byu.cs.tweeter.shared.model.domain.AuthToken;

public class FollowRequest {
    private AuthToken authToken;
    private boolean isFollowRequest;
    private String userAlias;
    private String followerAlias;

    public FollowRequest() {
    }

    public FollowRequest(AuthToken authToken, boolean isFollowRequest, String userAlias, String followerAlias) {
        this.authToken = authToken;
        this.isFollowRequest = isFollowRequest;
        this.userAlias = userAlias;
        this.followerAlias = followerAlias;
    }

    public AuthToken getAuthToken() {
        return authToken;
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

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public void setFollowRequest(boolean followRequest) {
        isFollowRequest = followRequest;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public void setFollowerAlias(String followerAlias) {
        this.followerAlias = followerAlias;
    }
}

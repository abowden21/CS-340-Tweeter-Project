package edu.byu.cs.tweeter.model.service.request;

public class StoryRequest {

    private final String userAlias;
    private final int limit;
    private final String lastFolloweeAlias;

    public StoryRequest(String followerAlias, int limit, String lastFolloweeAlias) {
        this.userAlias = followerAlias;
        this.limit = limit;
        this.lastFolloweeAlias = lastFolloweeAlias;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public String getLastFolloweeAlias() {
        return lastFolloweeAlias;
    }

    public int getLimit() {
        return limit;
    }
}

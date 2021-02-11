package edu.byu.cs.tweeter.model.service.request;

public class StoryRequest {

    private final String followerAlias;
    private final int limit;
    private final String lastFolloweeAlias;

    public StoryRequest(String followerAlias, int limit, String lastFolloweeAlias) {
        this.followerAlias = followerAlias;
        this.limit = limit;
        this.lastFolloweeAlias = lastFolloweeAlias;
    }

    public String getFollowerAlias() {
        return followerAlias;
    }

    public String getLastFolloweeAlias() {
        return lastFolloweeAlias;
    }

    public int getLimit() {
        return limit;
    }
}

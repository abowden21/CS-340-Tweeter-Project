package edu.byu.cs.tweeter.shared.model.request;

public class UserFollowCountRequest {
    private String alias;

    public UserFollowCountRequest(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }
}

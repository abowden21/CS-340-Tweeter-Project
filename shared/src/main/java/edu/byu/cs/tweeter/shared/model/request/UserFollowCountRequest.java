package edu.byu.cs.tweeter.shared.model.request;

public class UserFollowCountRequest {
    private String alias;

    public UserFollowCountRequest() {}

    public UserFollowCountRequest(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}

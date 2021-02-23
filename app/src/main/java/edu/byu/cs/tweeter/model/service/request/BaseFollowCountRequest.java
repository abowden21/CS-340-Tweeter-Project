package edu.byu.cs.tweeter.model.service.request;

public class BaseFollowCountRequest {
    private String userAlias;

    public BaseFollowCountRequest(String alias) {
        this.userAlias = alias;
    }

    public String getUserAlias() {
        return this.userAlias;
    }
}

package edu.byu.cs.tweeter.shared.model.request;

public class GetUserRequest {
    private String alias;

    public GetUserRequest(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}

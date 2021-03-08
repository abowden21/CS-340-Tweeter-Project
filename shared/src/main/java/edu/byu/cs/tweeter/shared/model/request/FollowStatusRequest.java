package edu.byu.cs.tweeter.shared.model.request;

public class FollowStatusRequest {
    private String loggedInUserAlias;
    private String otherUserAlias;

    public FollowStatusRequest(String loggedInUserAlias, String otherUserAlias) {
        this.loggedInUserAlias = loggedInUserAlias;
        this.otherUserAlias = otherUserAlias;
    }

    public String getLoggedInUserAlias() {
        return loggedInUserAlias;
    }

    public String getOtherUserAlias() {
        return otherUserAlias;
    }
}
